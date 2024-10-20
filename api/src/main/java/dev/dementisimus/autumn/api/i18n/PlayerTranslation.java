/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.i18n;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Allows the generation of message translations for players
 *
 * @since 1.0.0
 */
public interface PlayerTranslation extends Translation {

    /**
     * Gets the translation for a {@link Player}
     *
     * @param player player
     * @return translation in given player locale
     * @since 1.0.0
     */
    @NotNull String get(Player player);

    /**
     * Sends the translation to a {@link Player}
     *
     * @param player player
     * @since 1.1.1
     */
    void send(Player player);
}
