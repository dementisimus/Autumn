/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.input;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Used to retrieve text input from users via chat
 *
 * @since 1.0.0
 */
public interface UserTextInput {

    /**
     * Sets the message prefix
     *
     * @param prefix prefix
     *
     * @since 1.0.0
     */
    void prefix(@NotNull String prefix);

    /**
     * Sets the message property
     *
     * @param translationProperty property
     *
     * @since 1.0.0
     */
    void translationProperty(@NotNull String translationProperty);

    /**
     * Sets the player who will be prompted to enter text
     *
     * @param player player
     *
     * @since 1.0.0
     */
    void player(@NotNull Player player);

    /**
     * Fetches text from an user
     *
     * @param stringCallback the callback used to deliver the entered string
     *
     * @since 1.0.0
     */
    void fetch(@NotNull AutumnCallback<@NotNull String> stringCallback);
}
