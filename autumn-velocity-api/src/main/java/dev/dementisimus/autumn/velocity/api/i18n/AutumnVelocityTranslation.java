/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.api.i18n;

import com.velocitypowered.api.proxy.Player;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import org.jetbrains.annotations.NotNull;

/**
 * Allows the generation of message translations for velocity players
 *
 * @since 1.1.2
 */
public interface AutumnVelocityTranslation extends AutumnTranslation {

    /**
     * Gets the translation for a {@link Player}
     *
     * @param player player
     *
     * @return translation in given player locale
     *
     * @since 1.1.2
     */
    @NotNull String get(@NotNull Player player);

    /**
     * Sends the translation to a {@link Player}
     *
     * @param player player
     *
     * @since 1.1.2
     */
    void send(@NotNull Player player);
}
