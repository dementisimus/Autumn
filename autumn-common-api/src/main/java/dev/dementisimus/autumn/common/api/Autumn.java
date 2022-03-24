/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api;

import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import dev.dementisimus.autumn.common.api.injection.AutumnInjector;
import dev.dementisimus.autumn.common.api.schematic.AutumnPluginSchematic;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Autumn's main class
 *
 * @since 1.0.0
 */
public interface Autumn extends AutumnPluginSchematic {

    /**
     * Enables all default setup states: CONSOLE_LANGUAGE
     *
     * @since 1.0.0
     */
    void defaultSetupStates();

    /**
     * Enables all setup states needed for storage connections
     *
     * @since 1.0.0
     */
    void storageSetupStates();

    /**
     * Enables extra setup states
     *
     * @param setupStates extra setup states
     *
     * @since 1.0.0
     */
    void extraSetupStates(@NotNull SetupState... setupStates);

    /**
     * Enables an extra setup state
     *
     * @param setupState extra setup state
     *
     * @since 1.1.0
     */
    void extraSetupState(@NotNull SetupState setupState);

    /**
     * Initializes Autumn and its plugin
     *
     * @param initializationCallback the injector for adding plugin modules when the initialization has been completed
     *
     * @since 1.0.0
     */
    void initialize(@NotNull AutumnSingleCallback<@NotNull AutumnInjector> initializationCallback);

    /**
     * Provides the storage functionality (needs at least {@link #storageSetupStates()}
     *
     * @param storageSourceProperties storage source properties
     *
     * @since 1.0.0
     */
    void useStorage(@NotNull StorageSourceProperty... storageSourceProperties);

    /**
     * Checks if optional commands will be registered
     *
     * @return true if optional commands will be registered, false otherwise
     *
     * @since 1.0.0
     */
    boolean optionalCommands();

    /**
     * Sets the state of if optional commands will be registered
     *
     * @param optionalCommands register optional commands
     *
     * @since 1.0.0
     */
    void optionalCommands(boolean optionalCommands);

    /**
     * Checks if optional listeners will be registered
     *
     * @return true if optional listeners will be registered, false otherwise
     *
     * @since 1.0.0
     */
    boolean optionalListeners();

    /**
     * Sets the state of if optional listeners will be registered
     *
     * @param optionalListeners register optional listeners
     *
     * @since 1.0.0
     */
    void optionalListeners(boolean optionalListeners);

    /**
     * Checks if the injection will be skipped
     *
     * @return true if the injection will be skipped, false otherwise
     *
     * @since 1.1.1
     */
    boolean skipInjection();

    /**
     * Enables/disables the injection done by Autumn
     *
     * @param skipInjection skipInjection
     *
     * @since 1.1.1
     */
    void skipInjection(boolean skipInjection);
}
