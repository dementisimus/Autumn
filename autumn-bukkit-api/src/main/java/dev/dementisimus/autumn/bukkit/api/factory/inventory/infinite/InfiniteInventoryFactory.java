/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.inventory.infinite;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A factory for infinite inventories
 *
 * @since 1.0.0
 */
public interface InfiniteInventoryFactory {

    /**
     * Sets the items for the infinite inventory
     *
     * @param items the items
     * @param clazz the item class type
     *
     * @since 1.0.0
     */
    <T> void items(@NotNull List<T> items, @NotNull Class<T> clazz);

    /**
     * Creates the infinite inventory for a player and opens it when {@link #create()} has been called
     *
     * @param createFor create for {@link Player}
     *
     * @since 1.0.0
     */
    void createFor(@NotNull Player createFor);

    /**
     * Creates the infinite inventory and opens it for {@link #createFor(Player)}
     *
     * @since 1.0.0
     */
    void create();
}
