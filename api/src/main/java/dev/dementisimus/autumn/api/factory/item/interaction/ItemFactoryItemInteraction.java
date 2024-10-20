/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.factory.item.interaction;

import dev.dementisimus.autumn.api.factory.item.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an item factory item interaction
 *
 * @since 1.2.0
 */
public interface ItemFactoryItemInteraction {

    /**
     * Gets the item
     *
     * @return the item
     * @since 1.2.0
     */
    @NotNull ItemStack item();

    /**
     * Gets the {@link ItemFactory}
     *
     * @return the item factory
     * @since 1.2.0
     */
    @NotNull ItemFactory itemFactory();
}
