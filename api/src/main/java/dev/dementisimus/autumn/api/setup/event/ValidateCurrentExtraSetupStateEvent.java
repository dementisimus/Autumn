/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.setup.event;

import dev.dementisimus.autumn.api.setup.state.SetupState;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event to validate console input for a setup state
 *
 * @since 1.0.0
 */
public interface ValidateCurrentExtraSetupStateEvent {

    /**
     * Gets the current setup state
     *
     * @return the current setup state
     * @since 1.0.0
     */
    @NotNull SetupState currentSetupState();

    /**
     * Gets the console input
     *
     * @return the console input
     * @since 1.0.0
     */
    @NotNull String consoleInput();

    /**
     * Gets the validity of {@link #consoleInput()}
     *
     * @return true if the input was valid, false otherwise
     * @since 1.0.0
     */
    boolean validInput();

    /**
     * Sets the validity of {@link #consoleInput()}
     *
     * @param validInput the validity of {@link #consoleInput()}
     * @since 1.0.0
     */
    void validInput(boolean validInput);
}
