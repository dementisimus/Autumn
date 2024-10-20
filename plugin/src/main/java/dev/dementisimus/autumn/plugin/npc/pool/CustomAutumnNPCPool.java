/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.npc.pool;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedEnumEntityUseAction;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import dev.dementisimus.autumn.api.callback.DoubleCallback;
import dev.dementisimus.autumn.api.i18n.PlayerTranslation;
import dev.dementisimus.autumn.api.npc.AutumnNPC;
import dev.dementisimus.autumn.api.npc.pool.AutumnNPCPool;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.npc.CustomAutumnNPC;
import dev.dementisimus.autumn.plugin.reflection.ReflectionMethod;
import dev.dementisimus.autumn.plugin.util.ItemStackSerializer;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomAutumnNPCPool implements AutumnNPCPool, Listener {

    private static final Type NPC_TYPE = new TypeToken<ArrayList<CustomAutumnNPC>>() {
    }.getType();
    //Entity.java/ENTITY_COUNTER
    private static final AtomicInteger ENTITY_COUNT = Objects.requireNonNull(ReflectionMethod.getterStatic(net.minecraft.world.entity.Entity.class, "c", AtomicInteger.class)).get();

    private static final Random RANDOM = new Random();
    private static Gson gson;
    @Getter
    private final Map<Integer, AutumnNPC> npcsByEntityId = new HashMap<>();
    private final List<RequestedInteraction> requestedInteractions = new ArrayList<>();
    @Getter
    private final Map<String, DoubleCallback<Player, PlayerTranslation>> translationTasks = new HashMap<>();

    @Getter
    private final CustomAutumn autumn;
    @Getter
    private final Plugin plugin;

    public CustomAutumnNPCPool(CustomAutumn autumn, Plugin plugin) {
        this.autumn = autumn;
        this.plugin = plugin;

        this.loadConfigNPCs();

        Bukkit.getPluginManager().registerEvents(this, this.plugin);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packetContainer = event.getPacket();
                int entityId = packetContainer.getIntegers().read(0);
                Player player = event.getPlayer();
                CustomAutumnNPC npc = (CustomAutumnNPC) CustomAutumnNPCPool.this.npcsByEntityId.get(entityId);

                if (npc != null) {
                    List<DoubleCallback<Player, AutumnNPC.AutumnNPCInteractionAction>> interactions = new ArrayList<>();

                    if (npc.getInteraction() != null) {
                        interactions.add(npc.getInteraction());
                    }

                    if (npc.getId() != null) {
                        for (RequestedInteraction requestedInteraction : CustomAutumnNPCPool.this.requestedInteractions) {
                            if (requestedInteraction.id().equals(npc.getId())) {
                                interactions.add(requestedInteraction.interaction());
                            }
                        }
                    }

                    AutumnNPC.AutumnNPCInteractionAction interactionAction;
                    EnumWrappers.Hand hand;

                    WrappedEnumEntityUseAction enumEntityUseAction = packetContainer.getEnumEntityUseActions().read(0);
                    interactionAction = AutumnNPC.AutumnNPCInteractionAction.valueOf(enumEntityUseAction.getAction().name());
                    hand = interactionAction == AutumnNPC.AutumnNPCInteractionAction.ATTACK ? EnumWrappers.Hand.MAIN_HAND : enumEntityUseAction.getHand();

                    if (hand == EnumWrappers.Hand.OFF_HAND || interactionAction == AutumnNPC.AutumnNPCInteractionAction.INTERACT_AT)
                        return;

                    Bukkit.getScheduler().runTask(CustomAutumnNPCPool.this.plugin, () -> {
                        for (DoubleCallback<Player, AutumnNPC.AutumnNPCInteractionAction> interactionRequest : interactions) {
                            interactionRequest.done(player, interactionAction);
                        }
                    });
                }
            }
        });
    }

    @Override
    public AutumnNPC register(AutumnNPC npc) {
        CustomAutumnNPC customAutumnNPC = ((CustomAutumnNPC) npc);

        if (customAutumnNPC.getSkinValue() == null || customAutumnNPC.getSkinSignature() == null) {
            this.autumn.getLogger().error("No skin value and/or signature defined for npc!");
            return null;
        }

        WrappedGameProfile gameProfile = new WrappedGameProfile(new UUID(RANDOM.nextLong(), 0), RandomStringUtils.randomAlphanumeric(3));

        WrappedSignedProperty signedProperty = new WrappedSignedProperty("textures", customAutumnNPC.getSkinValue(), customAutumnNPC.getSkinSignature());
        gameProfile.getProperties().put(signedProperty.getName(), signedProperty);

        customAutumnNPC.setGameProfile(gameProfile);

        int entityId = ENTITY_COUNT.incrementAndGet();

        customAutumnNPC.setEntityId(entityId);

        customAutumnNPC.setNpcPool(this);

        this.npcsByEntityId.put(entityId, npc);

        return npc;
    }

    @Override
    public AutumnNPC unregister(AutumnNPC npc) {
        npc.hide();

        this.npcsByEntityId.remove(npc.getEntityId());

        for (TextDisplay textDisplay : ((CustomAutumnNPC) npc).getTextDisplays().values()) {
            textDisplay.remove();
        }

        return npc;
    }

    @Override
    public void onInteraction(String id, DoubleCallback<@NotNull Player, AutumnNPC.@NotNull AutumnNPCInteractionAction> interaction) {
        this.requestedInteractions.add(new RequestedInteraction(id, interaction));
    }

    @Override
    public void onTranslation(String id, DoubleCallback<@NotNull Player, @NotNull PlayerTranslation> context) {
        this.translationTasks.put(id, context);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            this.doNPCCheck(event.getPlayer(), null, event.getPlayer().getLocation());
        }, 5);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        this.doNPCCheck(event.getPlayer(), event.getPlayer().getLocation(), event.getRespawnLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        this.doNPCCheck(player, event.getFrom(), event.getTo());

        for (AutumnNPC npc : this.npcsByEntityId.values()) {
            if (npc.canSee(player)) {
                if (((CustomAutumnNPC) npc).isPushBack() && ((CustomAutumnNPC) npc).getBoundingBox().overlaps(((CustomAutumnNPC) npc).boundingBoxOf(event.getTo()))) {
                    Vector vector = event.getFrom().toVector().subtract(((CustomAutumnNPC) npc).getLocation().toVector()).normalize();
                    vector = vector.multiply(0.8);
                    vector = vector.setY(0.5);

                    if (Math.abs(vector.getX()) + Math.abs(vector.getZ()) <= 0.3) {
                        vector = vector.setY(0);
                        vector = vector.normalize();
                        vector = vector.multiply(0.8);
                        vector = vector.setY(0.5);
                    }

                    if (!Double.isFinite(vector.getX()) || !Double.isFinite(vector.getY()) || !Double.isFinite(vector.getZ())) {
                        vector = new Vector(0.5, 0.5, 0.5);
                    }

                    player.setVelocity(vector);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event) {
        for (Entity passenger : event.getVehicle().getPassengers()) {
            if (passenger instanceof Player player) {
                this.doNPCCheck(player, event.getFrom(), event.getTo());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        this.doNPCCheck(event.getPlayer(), event.getFrom(), event.getTo());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChangedWorld(PlayerChangedWorldEvent event) {
        this.doNPCCheck(event.getPlayer(), event.getFrom().getSpawnLocation(), event.getPlayer().getLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        for (AutumnNPC npc : this.npcsByEntityId.values()) {
            if (npc.canSee(event.getPlayer())) {
                npc.hide(event.getPlayer());
            }
        }
    }

    public void loadConfigNPCs() {
        JsonObject npcStorageObject = this.loadJsonFile(this.plugin.getDataFolder(), "npcs.json", JsonObject.class, new JsonObject());
        if (npcStorageObject.has("npcs")) {
            List<CustomAutumnNPC> npcs = this.gson().fromJson(npcStorageObject.getAsJsonArray("npcs"), NPC_TYPE);
            for (CustomAutumnNPC npc : npcs) {
                this.register(npc.deserialize(true));
            }
        }
    }

    public void doNPCCheck(Player player, Location from, Location to) {
        if (to == null || !to.isWorldLoaded()) return;
        World toWorld = to.getWorld();

        boolean skipVisibilityCheck = false;
        if (from != null) {
            if (!Objects.equals(from.getWorld(), toWorld)) {
                for (AutumnNPC autumnNPC : this.npcsByEntityId.values()) {
                    if (autumnNPC.canSee(player) && !player.getWorld().equals(((CustomAutumnNPC) autumnNPC).getLocation().getWorld())) {
                        autumnNPC.hide(player);
                    }
                }
            } else if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
                return;
            } else if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ())
                skipVisibilityCheck = true;
        }

        if (!skipVisibilityCheck) {
            for (AutumnNPC npc : this.npcsByEntityId.values()) {
                if (!((CustomAutumnNPC) npc).getLocation().getWorld().equals(to.getWorld())) {
                    npc.hide(player);
                    continue;
                }

                if (toWorld != null && !toWorld.isChunkLoaded(((CustomAutumnNPC) npc).getLocation().getChunk())) {
                    npc.hide(player);
                    continue;
                }

                double distance = ((CustomAutumnNPC) npc).getLocation().distanceSquared(to);
                boolean canSee = npc.canSee(player);

                if (!canSee && distance <= (((CustomAutumnNPC) npc).getSpawnDistance2())) {
                    Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                        npc.show(player);
                    }, 1);
                } else if (canSee && distance > (((CustomAutumnNPC) npc).getSpawnDistance2())) {
                    npc.hide(player);
                }
            }
        }

        for (AutumnNPC npc : this.npcsByEntityId.values()) {
            if (npc.canSee(player)) {
                if (((CustomAutumnNPC) npc).isLookAtPlayer()) {
                    if (((CustomAutumnNPC) npc).getLocation().distanceSquared(to) <= ((CustomAutumnNPC) npc).getActionDistance2()) {
                        npc.lookAt(player, player);
                    } else if (from != null) {
                        if (((CustomAutumnNPC) npc).getLocation().distanceSquared(from) <= ((CustomAutumnNPC) npc).getActionDistance2()) {
                            npc.lookAtDefaultLocation(player);
                        }
                    }
                }
            }
        }
    }

    private Gson gson() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();

            gsonBuilder.setPrettyPrinting();
            gsonBuilder.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC);

            gsonBuilder.registerTypeAdapter(ItemStack.class, new TypeAdapter<ItemStack>() {
                @Override
                public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext jsonSerializationContext) {
                    return new JsonPrimitive(ItemStackSerializer.itemToString(itemStack));
                }

                @Override
                public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return ItemStackSerializer.stringToItem(jsonElement.getAsString());
                }
            });

            gsonBuilder.registerTypeAdapter(Location.class, new TypeAdapter<Location>() {
                @Override
                public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("x", location.getX());
                    jsonObject.addProperty("y", location.getY());
                    jsonObject.addProperty("z", location.getZ());
                    jsonObject.addProperty("yaw", location.getYaw());
                    jsonObject.addProperty("pitch", location.getPitch());
                    jsonObject.addProperty("world", location.getWorld().getName());

                    return jsonObject;
                }

                @Override
                public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    double x = jsonObject.get("x").getAsDouble();
                    double y = jsonObject.get("y").getAsDouble();
                    double z = jsonObject.get("z").getAsDouble();
                    float yaw = jsonObject.get("yaw").getAsFloat();
                    float pitch = jsonObject.get("pitch").getAsFloat();
                    String world = jsonObject.get("world").getAsString();

                    return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                }
            });

            gson = gsonBuilder.create();
        }

        return gson;
    }

    private <T> T loadJsonFile(File dataFolder, String fileName, Class<T> clazz, T defaultValue) {
        File file = new File(dataFolder, fileName);

        if (!file.exists()) {
            return defaultValue;
        }

        try (Reader reader = new FileReader(file)) {
            return this.gson().fromJson(reader, clazz);
        } catch (IOException ex) {
            this.autumn.getLogger().error(ex);
            return defaultValue;
        }
    }

    private abstract static class TypeAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    }

    private record RequestedInteraction(String id,
                                        DoubleCallback<Player, AutumnNPC.AutumnNPCInteractionAction> interaction) {
    }
}
