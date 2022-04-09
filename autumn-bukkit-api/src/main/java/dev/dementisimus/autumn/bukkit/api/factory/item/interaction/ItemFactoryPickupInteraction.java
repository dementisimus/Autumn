/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item.interaction;

import org.bukkit.event.entity.EntityPickupItemEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an item factory item pickup interaction
 *
 * @since 1.2.1
 */
public interface ItemFactoryPickupInteraction extends ItemFactoryPlayerInteraction, ItemFactoryItemInteraction, ItemFactoryCancellableInteraction {

    /**
     * Gets the {@link EntityPickupItemEvent}
     *
     * @return the {@link EntityPickupItemEvent}
     *
     * @since 1.2.1
     */
    @NotNull EntityPickupItemEvent event();
}
