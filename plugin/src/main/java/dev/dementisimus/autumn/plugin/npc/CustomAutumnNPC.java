/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import dev.dementisimus.autumn.api.callback.DoubleCallback;
import dev.dementisimus.autumn.api.i18n.PlayerTranslation;
import dev.dementisimus.autumn.api.npc.AutumnNPC;
import dev.dementisimus.autumn.api.packet.PacketContainerEvent;
import dev.dementisimus.autumn.plugin.i18n.CustomPlayerTranslation;
import dev.dementisimus.autumn.plugin.npc.pool.CustomAutumnNPCPool;
import dev.dementisimus.autumn.plugin.packet.CustomPacketContainerEvent;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minecraft.world.scores.Team;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

@Setter
@Getter
public class CustomAutumnNPC implements AutumnNPC {

    private static final EnumSet<EnumWrappers.PlayerInfoAction> ACTIONS_ADD = EnumSet.of(EnumWrappers.PlayerInfoAction.ADD_PLAYER, EnumWrappers.PlayerInfoAction.UPDATE_LATENCY, EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);

    private transient final Set<UUID> visibleFor = new HashSet<>();
    private final Map<EquipmentSlot, ItemStack> equipment = new HashMap<>();
    private Location location;
    private String translationKey;
    private transient DoubleCallback<Player, PlayerTranslation> translationContext;
    private transient CustomAutumnNPCPool npcPool;
    private transient BoundingBox boundingBox;
    private transient boolean configNPC;
    private transient float defaultLocationYaw;
    private transient float defaultLocationPitch;

    private transient Integer entityId;
    private transient WrappedGameProfile gameProfile;

    private String skinValue;
    private String skinSignature;
    private int spawnDistance = 20;
    private transient int spawnDistance2 = 20 * 20;
    private int actionDistance = 5;
    private transient int actionDistance2 = 5 * 5;
    private boolean lookAtPlayer;
    private boolean pushBack = true;
    private String id;
    private transient DoubleCallback<Player, AutumnNPCInteractionAction> interaction;
    private transient Map<UUID, TextDisplay> textDisplays = new HashMap<>();

    @ApiStatus.Internal
    public CustomAutumnNPC() {
    }

    public CustomAutumnNPC(Location location) {
        this.location = location;

        this.deserialize(false);
    }

    @Override
    public AutumnNPC translated(String translationKey, DoubleCallback<@NotNull Player, @NotNull PlayerTranslation> context) {
        this.translationKey = translationKey;
        return this.translated(context);
    }

    @Override
    public AutumnNPC translated(DoubleCallback<@NotNull Player, @NotNull PlayerTranslation> context) {
        this.translationContext = context;
        return this;
    }

    @Override
    public AutumnNPC setSkinValue(String skinValue) {
        this.skinValue = skinValue;
        return this;
    }

    @Override
    public AutumnNPC setSkinSignature(String skinSignature) {
        this.skinSignature = skinSignature;
        return this;
    }

    @Override
    public AutumnNPC setSpawnDistance(int spawnDistance) {
        this.spawnDistance = spawnDistance;
        this.spawnDistance2 = this.spawnDistance * this.spawnDistance;
        return this;
    }

    @Override
    public AutumnNPC setActionDistance(int actionDistance) {
        this.actionDistance = actionDistance;
        this.actionDistance2 = this.actionDistance * this.actionDistance;
        return this;
    }

    @Override
    public AutumnNPC setLookAtPlayer(boolean lookAtPlayer) {
        this.lookAtPlayer = lookAtPlayer;
        return this;
    }

    @Override
    public AutumnNPC setPushBack(boolean pushBack) {
        this.pushBack = pushBack;
        return this;
    }

    @Override
    public AutumnNPC setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public AutumnNPC onInteraction(DoubleCallback<@NotNull Player, @NotNull AutumnNPCInteractionAction> interaction) {
        this.interaction = interaction;
        return this;
    }

    @Override
    public AutumnNPC setEquipment(EquipmentSlot slot, ItemStack itemStack) {
        this.equipment.put(slot, itemStack);
        return this;
    }

    @Override
    public AutumnNPC setEquipment(Player player, EquipmentSlot slot, ItemStack itemStack) {
        if (!this.equipment.get(slot).equals(itemStack)) {
            this.equipment.put(slot, itemStack);
        }

        EnumWrappers.ItemSlot itemSlot = switch (slot) {
            case HAND -> EnumWrappers.ItemSlot.MAINHAND;
            case OFF_HAND -> EnumWrappers.ItemSlot.OFFHAND;
            default -> EnumWrappers.ItemSlot.valueOf(slot.name());
        };

        this.send(player, PacketType.Play.Server.ENTITY_EQUIPMENT, packetContainerEvent -> {
            packetContainerEvent.getPacketContainer().getSlotStackPairLists().write(0, List.of(new Pair<>(itemSlot, itemStack)));
        });

        return this;
    }

    @Override
    public AutumnNPC setRotation(Player player, float yaw, float pitch) {
        if (!this.canSee(player)) return this;

        byte yawAngle = (byte) ((int) (yaw * 256 / 360));
        byte pitchAngle = (byte) ((int) (pitch * 256 / 360));

        this.send(player, PacketType.Play.Server.ENTITY_LOOK, packetContainerEvent -> {
            packetContainerEvent.getPacketContainer().getBytes().write(0, yawAngle);
            packetContainerEvent.getPacketContainer().getBytes().write(1, pitchAngle);
        });

        this.send(player, PacketType.Play.Server.ENTITY_HEAD_ROTATION, containerEvent -> {
            containerEvent.getPacketContainer().getBytes().write(0, yawAngle);
        });

        return this;
    }

    @Override
    public AutumnNPC lookAtDefaultLocation(Player player) {
        this.setRotation(player, this.defaultLocationYaw, this.defaultLocationPitch);

        return this;
    }

    @Override
    public AutumnNPC lookAt(Player player, Location location) {
        double dX = location.getX() - this.location.getX();
        double xY = location.getY() - this.location.getY();
        double dZ = location.getZ() - this.location.getZ();

        float yaw = (float) (-Math.atan2(dX, dZ) / Math.PI * 180);
        yaw = yaw < 0 ? yaw + 360 : yaw;

        return this.setRotation(player, yaw, (float) (-Math.asin(xY / Math.sqrt(Math.pow(dX, 2) + Math.pow(xY, 2) + Math.pow(dZ, 2))) / Math.PI * 180));
    }

    @Override
    public void show(Player player) {
        if (this.visibleFor.add(player.getUniqueId()) && player.getWorld().equals(this.location.getWorld())) {
            if (player.getLocation().distanceSquared(this.location) <= (this.spawnDistance2)) {
                this.send(player, new PacketContainer(PacketType.Play.Server.PLAYER_INFO), packetContainerEvent -> {
                    PacketContainer packetContainer = packetContainerEvent.getPacketContainer();

                    packetContainer.getPlayerInfoActions().write(0, ACTIONS_ADD);

                    packetContainer.getPlayerInfoDataLists().write(1, new ArrayList<>(Collections.singletonList(new PlayerInfoData(this.gameProfile.getUUID(), 20, false, EnumWrappers.NativeGameMode.CREATIVE, this.gameProfile, WrappedChatComponent.fromText(""), (WrappedRemoteChatSessionData) null))));
                });

                this.send(player, PacketType.Play.Server.SPAWN_ENTITY, packetContainerEvent -> {
                    packetContainerEvent.getPacketContainer().getUUIDs().write(0, this.gameProfile.getUUID());
                    packetContainerEvent.getPacketContainer().getEntityTypeModifier().write(0, EntityType.PLAYER);

                    packetContainerEvent.getPacketContainer().getDoubles().write(0, this.location.getX());
                    packetContainerEvent.getPacketContainer().getDoubles().write(1, this.location.getY());
                    packetContainerEvent.getPacketContainer().getDoubles().write(2, this.location.getZ());

                    packetContainerEvent.getPacketContainer().getBytes().write(0, (byte) ((int) (this.location.getYaw() * 256.0F / 360.0F)));
                    packetContainerEvent.getPacketContainer().getBytes().write(1, (byte) ((int) (this.location.getPitch() * 256.0F / 360.0F)));
                });

                this.equipment.forEach((equipmentSlot, itemStack) -> {
                    this.setEquipment(player, equipmentSlot, itemStack);
                });

                this.sendTeam(player, 0);
                this.sendTeam(player, 3);
                this.sendTeam(player, 2);

                this.lookAt(player, this.location);

                if (this.translationKey != null) {
                    if (!this.textDisplays.containsKey(player.getUniqueId())) {
                        this.textDisplays.put(player.getUniqueId(), player.getWorld().spawn(this.location.clone().add(0, 1.95, 0), TextDisplay.class, display -> {
                            display.setRotation(this.defaultLocationYaw, this.defaultLocationPitch);
                            display.setLineWidth(Integer.MAX_VALUE);
                            display.setBillboard(Display.Billboard.VERTICAL);
                            display.setDefaultBackground(false);
                            display.setBackgroundColor(Color.fromARGB(0));
                            display.setShadowed(true);
                            display.setViewRange(0.5f);
                            display.setDisplayWidth(1);
                            display.setDisplayHeight(2);
                            display.setPersistent(false);
                            display.setVisibleByDefault(false);

                            PlayerTranslation translation = new CustomPlayerTranslation(this.translationKey);
                            DoubleCallback<Player, PlayerTranslation> translationContext = this.translationContext;

                            if (translationContext == null && this.id != null) {
                                translationContext = this.npcPool.getTranslationTasks().get(this.id);
                            }

                            if (translationContext != null) {
                                translationContext.done(player, translation);
                            }

                            display.text(Component.text(translation.get(player)));
                        }));
                    }

                    player.showEntity(this.npcPool.getPlugin(), this.textDisplays.get(player.getUniqueId()));
                }
            }
        }
    }

    @Override
    public void hide(Player player) {
        if (this.visibleFor.remove(player.getUniqueId()) && player.getWorld().equals(this.location.getWorld())) {
            this.send(player, new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY), packetContainerEvent -> {
                packetContainerEvent.getPacketContainer().getIntLists().write(0, new ArrayList<>(Collections.singletonList(this.entityId)));
                packetContainerEvent.runAfter(() -> {
                    if (this.textDisplays.containsKey(player.getUniqueId())) {
                        player.hideEntity(this.npcPool.getPlugin(), this.textDisplays.get(player.getUniqueId()));
                    }
                });
            });
        }
    }

    @Override
    public boolean canSee(Player player) {
        return this.visibleFor.contains(player.getUniqueId()) && player.getWorld().equals(this.location.getWorld());
    }

    public AutumnNPC deserialize(boolean fromConfig) {
        this.boundingBox = this.boundingBoxOf(this.location);
        this.defaultLocationYaw = this.location.getYaw();
        this.defaultLocationPitch = this.location.getPitch();
        this.spawnDistance2 = this.spawnDistance * this.spawnDistance;
        this.actionDistance2 = this.actionDistance * this.actionDistance;

        if (fromConfig) {
            this.configNPC = true;
        }

        return this;
    }

    public BoundingBox boundingBoxOf(Location location) {
        return BoundingBox.of(location.clone().add(0, 0.9, 0), 0.3, 0.9, 0.3);
    }

    private void sendTeam(Player player, int mode) {
        this.send(player, new PacketContainer(PacketType.Play.Server.SCOREBOARD_TEAM), packetContainerEvent -> {
            packetContainerEvent.getPacketContainer().getIntegers().write(0, mode);
            packetContainerEvent.getPacketContainer().getStrings().write(0, this.entityId + player.getName());

            if (mode == 0 || mode == 2) {
                packetContainerEvent.getPacketContainer().getOptionalTeamParameters().write(0, Optional.of(WrappedTeamParameters.newBuilder().displayName(WrappedChatComponent.fromText("")).prefix(WrappedChatComponent.fromText("")).suffix(WrappedChatComponent.fromText("")).nametagVisibility("never").collisionRule(Team.CollisionRule.NEVER.name).color(EnumWrappers.ChatFormatting.fromBukkit(ChatColor.WHITE)).build()));
            } else if (mode == 3 || mode == 4) {
                packetContainerEvent.getPacketContainer().getSpecificModifier(Collection.class).write(0, Collections.singletonList(this.gameProfile.getName()));
            }
        });
    }

    private void send(Player player, PacketType packetType, Consumer<PacketContainerEvent> packetContainerEventConsumer) {
        PacketContainer packetContainer = new PacketContainer(packetType);

        packetContainer.getIntegers().write(0, this.entityId);

        this.send(player, packetContainer, packetContainerEventConsumer);
    }

    private void send(Player player, PacketContainer packetContainer, Consumer<PacketContainerEvent> packetContainerEventConsumer) {
        PacketContainerEvent packetContainerEvent = new CustomPacketContainerEvent(player, this.entityId, packetContainer);

        if (packetContainerEventConsumer != null) {
            packetContainerEventConsumer.accept(packetContainerEvent);
        }

        packetContainerEvent.process();
    }
}
