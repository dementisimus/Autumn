/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item.interaction;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an item factory player interaction
 *
 * @since 1.1.2
 */
public interface ItemFactoryPlayerInteraction {

    /**
     * Gets the player
     *
     * @return the player
     *
     * @since 1.1.2
     */
    @NotNull Player player();
}
