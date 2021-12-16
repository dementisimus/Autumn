/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an item factory item interaction
 *
 * @since 1.1.0
 */
public interface ItemFactoryInteraction {

    /**
     * Gets the {@link PlayerInteractEvent}
     *
     * @return the {@link PlayerInteractEvent}
     *
     * @since 1.1.0
     */
    @NotNull PlayerInteractEvent event();

    /**
     * Gets the player
     *
     * @return the player
     *
     * @since 1.1.0
     */
    @NotNull Player player();

    /**
     * Gets the item
     *
     * @return the item
     *
     * @since 1.1.0
     */
    @NotNull ItemStack item();

    /**
     * Gets the {@link ItemFactory}
     *
     * @return the item factory
     *
     * @since 1.1.0
     */
    @NotNull ItemFactory itemFactory();

    /**
     * Gets the {@link Action} used in this interaction
     *
     * @return the used {@link Action}
     *
     * @since 1.1.0
     */
    @NotNull Action action();

    /**
     * Gets the {@link EquipmentSlot}
     *
     * @return the {@link EquipmentSlot}
     *
     * @since 1.1.0
     */
    @Nullable EquipmentSlot slot();

    /**
     * Gets the {@link Block}
     *
     * @return the {@link Block}
     *
     * @since 1.1.0
     */
    @Nullable Block block();

    /**
     * Gets the {@link BlockFace}
     *
     * @return the {@link BlockFace}
     *
     * @since 1.1.0
     */
    @Nullable BlockFace blockFace();
}
