/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.language;

import org.bukkit.entity.Player;

/**
 * Used for custom user language selection
 *
 * @since 1.0.0
 */
public interface LanguageSelection {

    /**
     * Opens the inventory for language selection
     *
     * @param player player
     * @since 1.0.0
     */
    void open(Player player);
}
