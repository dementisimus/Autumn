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
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class Autumn @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:18:56
 */
public interface Autumn {

    void defaultSetupStates();

    void databaseSetupStates();

    void extraSetupStates(SetupState... setupStates);

    void initialize(AutumnCallback<AutumnInjector> initializationCallback);

    void enableDatabase(DataSourceProperty... dataSourceProperties);

    boolean optionalCommands();

    void optionalCommands(boolean optionalCommands);

    boolean optionalListeners();

    void optionalListeners(boolean optionalListeners);

    AutumnTaskExecutor taskExecutor();

    AutumnLogging logging();

    AutumnInjector injector();

    AutumnLanguage defaultLanguage();

    SetupManager setupManager();

    Database database();
}
