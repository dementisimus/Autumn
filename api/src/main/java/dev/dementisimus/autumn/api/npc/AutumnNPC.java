/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.npc;

import dev.dementisimus.autumn.api.callback.DoubleCallback;
import dev.dementisimus.autumn.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.api.i18n.PlayerTranslation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface AutumnNPC {

    Integer getEntityId();

    AutumnNPC translated(String translationKey, DoubleCallback<@NotNull Player, @NotNull PlayerTranslation> context);

    AutumnNPC translated(DoubleCallback<@NotNull Player, @NotNull PlayerTranslation> context);

    AutumnNPC setSkinValue(String skinValue);

    AutumnNPC setSkinSignature(String skinSignature);

    AutumnNPC setSpawnDistance(int spawnDistance);

    AutumnNPC setActionDistance(int actionDistance);

    AutumnNPC setLookAtPlayer(boolean lookAtPlayer);

    AutumnNPC setPushBack(boolean pushBack);

    AutumnNPC setId(String id);

    AutumnNPC onInteraction(DoubleCallback<@NotNull Player, @NotNull AutumnNPCInteractionAction> interaction);

    AutumnNPC setEquipment(EquipmentSlot slot, ItemStack itemStack);

    AutumnNPC setEquipment(Player player, EquipmentSlot slot, ItemStack itemStack);

    AutumnNPC setRotation(Player player, float yaw, float pitch);

    AutumnNPC lookAtDefaultLocation(Player player);

    AutumnNPC lookAt(Player player, Location location);

    void show(Player player);

    void hide(Player player);

    boolean canSee(Player player);

    default AutumnNPC lookAt(Location location) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            this.lookAt(onlinePlayer);
        }
        return this;
    }

    default AutumnNPC lookAt(Player player) {
        return this.lookAt(player, player.getLocation());
    }

    default AutumnNPC lookAt(Player player, Player target) {
        return this.lookAt(player, target.getLocation());
    }

    default AutumnNPC lookAtDefaultLocation() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            this.lookAtDefaultLocation(onlinePlayer);
        }
        return this;
    }

    default void show() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            this.show(onlinePlayer);
        }
    }

    default void hide() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            this.hide(onlinePlayer);
        }
    }

    default AutumnNPC setEquipment(EquipmentSlot slot, ItemFactory itemFactory) {
        return this.setEquipment(slot, itemFactory.create());
    }

    default AutumnNPC setEquipment(EquipmentSlot slot, Material material) {
        return this.setEquipment(slot, new ItemStack(material));
    }

    default AutumnNPC setEquipment(Player player, EquipmentSlot slot, ItemFactory itemFactory) {
        return this.setEquipment(player, slot, itemFactory.create());
    }

    default AutumnNPC setEquipment(Player player, EquipmentSlot slot, Material material) {
        return this.setEquipment(player, slot, new ItemStack(material));
    }

    enum AutumnNPCInteractionAction {

        ATTACK,
        INTERACT,
        INTERACT_AT;

        public boolean isLeft() {
            return this == ATTACK;
        }

        public boolean isRight() {
            return !this.isLeft();
        }

        public boolean isAttack() {
            return this.isLeft();
        }
    }
}
