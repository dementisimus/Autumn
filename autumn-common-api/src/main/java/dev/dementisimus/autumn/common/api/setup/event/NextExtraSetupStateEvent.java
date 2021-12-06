/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.setup.event;

import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;

/**
 * Represents an event called when a next extra setup state is needed
 *
 * @since 1.0.0
 */
public interface NextExtraSetupStateEvent {

    /**
     * Gets the setup manager
     *
     * @return the setup manager
     *
     * @since 1.0.0
     */
    SetupManager setupManager();

    /**
     * Gets the current setup state
     *
     * @return the current setup state
     *
     * @since 1.0.0
     */
    SetupState currentSetupState();

    /**
     * Gets the current setup state list index
     *
     * @return the current setup state list index
     *
     * @since 1.0.0
     */
    int currentStateListIndex();

    /**
     * Gets the next setup state
     *
     * @return the next setup state
     *
     * @since 1.0.0
     */
    SetupState nextSetupState();

    /**
     * Sets the next setup state
     *
     * @param nextSetupState the next setup state
     *
     * @since 1.0.0
     */
    void nextSetupState(SetupState nextSetupState);

    /**
     * Gets the cancellation state of the event
     *
     * @return true if the event was cancelled, false otherwise
     *
     * @since 1.0.0
     */
    boolean cancelled();

    /**
     * Cancels the event, and the setup will be completed
     *
     * @param cancelled cancelled
     *
     * @since 1.0.0
     */
    void cancelled(boolean cancelled);
}
