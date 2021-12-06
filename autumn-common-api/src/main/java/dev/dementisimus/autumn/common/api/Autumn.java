/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.database.Database;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.injection.AutumnInjector;
import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;

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
     * Enables all setup states needed for database connections
     *
     * @since 1.0.0
     */
    void databaseSetupStates();

    /**
     * Enables extra setup states
     *
     * @param setupStates extra setup states
     *
     * @since 1.0.0
     */
    void extraSetupStates(SetupState... setupStates);

    /**
     * Initializes Autumn and its plugin
     *
     * @param initializationCallback the injector for adding plugin modules when the initialization has been completed
     *
     * @since 1.0.0
     */
    void initialize(AutumnCallback<AutumnInjector> initializationCallback);

    /**
     * Enables the database (needs at least {@link #databaseSetupStates()}
     *
     * @param dataSourceProperties data source properties
     *
     * @since 1.0.0
     */
    void enableDatabase(DataSourceProperty... dataSourceProperties);

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
    AutumnTaskExecutor taskExecutor();

    /**
     * Gets the {@link AutumnLogging}
     *
     * @return the {@link AutumnLogging}
     *
     * @since 1.0.0
     */
    AutumnLogging logging();

    /**
     * Gets the {@link AutumnInjector}
     *
     * @return the {@link AutumnInjector}
     *
     * @since 1.0.0
     */
    AutumnInjector injector();

    /**
     * Gets the default {@link AutumnLanguage}
     *
     * @return the default {@link AutumnLanguage}
     *
     * @since 1.0.0
     */
    AutumnLanguage defaultLanguage();

    /**
     * Gets the {@link SetupManager}
     *
     * @return the {@link SetupManager}
     *
     * @since 1.0.0
     */
    SetupManager setupManager();

    /**
     * Gets the {@link Database}, if enabled by {@link #enableDatabase(DataSourceProperty...)}
     *
     * @return the {@link Database}, if enabled, else null
     *
     * @since 1.0.0
     */
    Database database();
}
