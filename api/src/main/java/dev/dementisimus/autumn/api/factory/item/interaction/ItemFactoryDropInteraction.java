/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.factory.item.interaction;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an item factory drop item interaction
 *
 * @since 1.2.0
 */
public interface ItemFactoryDropInteraction extends ItemFactoryPlayerInteraction, ItemFactoryItemInteraction, ItemFactoryCancellableInteraction {

    /**
     * Gets the {@link PlayerDropItemEvent}
     *
     * @return the {@link PlayerDropItemEvent}
     * @since 1.2.0
     */
    @NotNull PlayerDropItemEvent event();
}
