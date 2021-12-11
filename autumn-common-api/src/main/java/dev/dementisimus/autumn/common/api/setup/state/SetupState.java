/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.setup.state;

import dev.dementisimus.autumn.common.api.setup.SetupManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Represents a setup state used in the setup process
 *
 * @since 1.0.0
 */
public interface SetupState {

    /**
     * Gets the setup state value
     *
     * @return the setup state value
     *
     * @since 1.0.0
     */
    Object value();

    /**
     * Gets the setup state value
     *
     * @param <T> setup state value
     * @param clazz setup state value class type
     *
     * @return the setup state value as class type
     *
     * @since 1.0.0
     */
    <T> T value(@NotNull Class<T> clazz);

    /**
     * Sets the setup state value
     *
     * @param value the setup state value
     *
     * @since 1.0.0
     */
    void value(@NotNull Object value);

    /**
     * Gets the message translation property shown to the user on setup
     *
     * @return the message translation property
     *
     * @since 1.0.0
     */
    @NotNull String messageTranslationProperty();

    /**
     * Gets the {@link #value()} as a String
     *
     * @return the {@link #value()} as a String
     *
     * @since 1.0.0
     */
    @Nullable String asString();

    /**
     * Gets the {@link #value()} as an Integer
     *
     * @return the {@link #value()} as an Integer
     *
     * @since 1.0.0
     */
    int asInteger();

    /**
     * Gets the {@link #value()} as a Boolean
     *
     * @return the {@link #value()} as a Boolean
     *
     * @since 1.0.0
     */
    boolean asBoolean();

    /**
     * Gets the {@link #value()} as a File
     *
     * @return the {@link #value()} as a File
     *
     * @since 1.0.0
     */
    @Nullable File asFile();

    /**
     * Checks if the setup state is present in the config file
     *
     * @param configFile config file
     *
     * @return true if the setup state is present in the config file, false otherwise
     *
     * @since 1.0.0
     */
    boolean isPresentInConfigFile(@NotNull File configFile);

    /**
     * Checks if the setup state is considered an extra state
     *
     * @param setupManager setup manager
     *
     * @return true, if the setup state is an extra state, false otherwise
     *
     * @since 1.0.0
     */
    boolean isExtraState(@NotNull SetupManager setupManager);

    /**
     * Gets the name of the setup state
     *
     * @return setup state name
     *
     * @since 1.0.0
     */
    @NotNull String name();
}
