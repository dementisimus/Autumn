/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bungee.api.i18n;

import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Allows the generation of message translations for players
 *
 * @since 1.0.0
 */
public interface AutumnBungeeTranslation extends AutumnTranslation {

    /**
     * Gets the translation for a {@link ProxiedPlayer}
     *
     * @param player player
     *
     * @return translation in given player locale
     *
     * @since 1.0.0
     */
    @NotNull String get(@NotNull ProxiedPlayer player);

    /**
     * Sends the translation to a {@link ProxiedPlayer}
     *
     * @param player player
     *
     * @since 1.1.1
     */
    void send(@NotNull ProxiedPlayer player);
}
