/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.injection.AutumnInjector;
import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Autumn's main class
 *
 * @since 1.0.0
 */
public interface Autumn {

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
     * Initializes Autumn and its plugin
     *
     * @param initializationCallback the injector for adding plugin modules when the initialization has been completed
     *
     * @since 1.0.0
     */
    void initialize(@NotNull AutumnCallback<@NotNull AutumnInjector> initializationCallback);

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
     * Gets the {@link AutumnTaskExecutor}
     *
     * @return the {@link AutumnTaskExecutor}
     *
     * @since 1.0.0
     */
    @NotNull AutumnTaskExecutor taskExecutor();

    /**
     * Gets the {@link AutumnLogging}
     *
     * @return the {@link AutumnLogging}
     *
     * @since 1.0.0
     */
    @NotNull AutumnLogging logging();

    /**
     * Gets the {@link AutumnInjector}
     *
     * @return the {@link AutumnInjector}
     *
     * @since 1.0.0
     */
    @NotNull AutumnInjector injector();

    /**
     * Gets the default {@link AutumnLanguage}
     *
     * @return the default {@link AutumnLanguage}
     *
     * @since 1.0.0
     */
    @NotNull AutumnLanguage defaultLanguage();

    /**
     * Gets the {@link SetupManager}
     *
     * @return the {@link SetupManager}
     *
     * @since 1.0.0
     */
    @NotNull SetupManager setupManager();

    /**
     * Gets the {@link Storage}, if enabled by {@link #useStorage(StorageSourceProperty...)}
     *
     * @return the {@link Storage}, if enabled, else null
     *
     * @since 1.0.0
     */
    @Nullable Storage storage();
}
