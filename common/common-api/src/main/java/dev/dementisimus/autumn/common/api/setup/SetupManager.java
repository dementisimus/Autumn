/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.setup;

import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The setup manager for Autumn
 *
 * @since 1.0.0
 */
public interface SetupManager {

    /**
     * Adds a main setup state to be used for setup
     *
     * @param setupState main setup state
     * @since 1.0.0
     */
    void mainSetupState(@NotNull SetupState setupState);

    /**
     * Adds an extra setup state to be used for setup
     *
     * @param setupState extra setup state
     * @since 1.0.0
     */
    void extraSetupState(@NotNull SetupState setupState);

    /**
     * Gets the current setup state
     *
     * @return the current setup state
     * @since 1.0.0
     */
    @Nullable SetupState currentSetupState();

    /**
     * Sets the current setup state
     *
     * @param setupState current setup state
     * @since 1.0.0
     */
    void currentSetupState(@NotNull SetupState setupState);

    /**
     * Prints the setup state instructions
     *
     * @param setupState setup state
     * @since 1.0.0
     */
    void printSetupStateInstructions(@NotNull SetupState setupState);

    /**
     * Updates the current setup state's value
     *
     * @param value current setup state's new value
     * @since 1.0.0
     */
    void updateCurrentSetupState(@NotNull Object value);

    /**
     * Begins the setup
     *
     * @since 1.0.0
     */
    void begin();

    /**
     * Completes the setup
     *
     * @param postSetup true if executed post-setup, false otherwise
     * @since 1.0.0
     */
    void complete(boolean postSetup);

    /**
     * Checks if the setup has been completed (or skipped)
     *
     * @return true if the setup has been completed (or skipped), false otherwise
     * @since 1.0.0
     */
    boolean isCompleted();

    /**
     * Checks if the setup state is considered an extra state
     *
     * @param setupState setup state
     * @return true, if the setup state is an extra state, false otherwise
     * @since 1.0.0
     */
    boolean isExtraState(@NotNull SetupState setupState);
}
