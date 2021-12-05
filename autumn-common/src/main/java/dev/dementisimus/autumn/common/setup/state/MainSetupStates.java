package dev.dementisimus.autumn.common.setup.state;

import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateDatabaseType;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateFile;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateInteger;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateLanguageType;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateString;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class MainSetupStates @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:58
 */
public class MainSetupStates {

    public static final SetupState CONSOLE_LANGUAGE = new SetupStateLanguageType("CONSOLE_LANGUAGE", "autumn.setup.console.language");
    public static final SetupState STORAGE = new SetupStateDatabaseType("STORAGE", "autumn.setup.storage");
    public static final SetupState MONGODB_URI = new SetupStateString("MONGODB_URI", "autumn.setup.mongodb.uri");
    public static final SetupState MARIADB_HOST = new SetupStateString("MARIADB_HOST", "autumn.setup.mariadb.host");
    public static final SetupState MARIADB_PORT = new SetupStateInteger("MARIADB_PORT", "autumn.setup.mariadb.port", 3306);
    public static final SetupState MARIADB_USER = new SetupStateString("MARIADB_USER", "autumn.setup.mariadb.user");
    public static final SetupState MARIADB_PASSWORD = new SetupStateString("MARIADB_PASSWORD", "autumn.setup.mariadb.password");
    public static final SetupState DATABASE = new SetupStateString("DATABASE", "autumn.setup.database");
    public static final SetupState SQLITE_FILE_PATH = new SetupStateFile("SQLITE_FILE_PATH", "autumn.setup.sqlite.file.path");

    public static SetupState[] values() {
        return new SetupState[]{CONSOLE_LANGUAGE, STORAGE, MONGODB_URI, MARIADB_HOST, MARIADB_PORT, MARIADB_USER, MARIADB_PASSWORD, DATABASE, SQLITE_FILE_PATH};
    }
}