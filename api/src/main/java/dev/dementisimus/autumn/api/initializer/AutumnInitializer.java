/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.initializer;

import dev.dementisimus.autumn.api.Autumn;
import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.setup.state.SetupState;
import dev.dementisimus.autumn.api.storage.property.source.StorageSourceProperty;

public interface AutumnInitializer {

    /**
     * Sets the prefix used for all messages sent by Autumn
     *
     * @since 2.0.0
     */
    AutumnInitializer setPluginPrefix(String prefix);

    /**
     * Enables all default setup states: CONSOLE_LANGUAGE
     *
     * @since 2.0.0
     */
    AutumnInitializer enableDefaultSetupStates();

    /**
     * Enables all setup states needed for storage connections
     *
     * @since 2.0.0
     */
    AutumnInitializer enableStorageSetupStates();

    /**
     * Enables extra setup states
     *
     * @param setupStates extra setup states
     * @since 2.0.0
     */
    AutumnInitializer enableExtraSetupStates(SetupState... setupStates);

    /**
     * Enables an extra setup state
     *
     * @param setupState extra setup state
     * @since 2.0.0
     */
    AutumnInitializer enableExtraSetupState(SetupState setupState);

    /**
     * Provides the storage functionality (needs at least {@link #enableStorageSetupStates()}
     *
     * @param storageSourceProperties storage source properties
     * @since 2.0.0
     */
    AutumnInitializer enableStorage(StorageSourceProperty... storageSourceProperties);

    /**
     * Initializes the Autumn instance
     *
     * @param initializationCallback callback with an Autumn instance for when initialization is done
     * @since 2.0.0
     */
    void initialize(SingleCallback<Autumn> initializationCallback);
}
