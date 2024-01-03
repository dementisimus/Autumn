/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.setup.state;

import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.plugin.setup.state.type.*;

public class MainSetupStates {

    public static final SetupState CONSOLE_LANGUAGE = new SetupStateLanguageType("CONSOLE_LANGUAGE", "autumn.setup.console.language");
    public static final SetupState STORAGE = new SetupStateStorageType("STORAGE", "autumn.setup.storage");
    public static final SetupState MONGODB_URI = new SetupStateString("MONGODB_URI", "autumn.setup.storage.mongodb.uri");
    public static final SetupState MARIADB_HOST = new SetupStateString("MARIADB_HOST", "autumn.setup.storage.mariadb.host");
    public static final SetupState MARIADB_PORT = new SetupStateInteger("MARIADB_PORT", "autumn.setup.storage.mariadb.port", 3306);
    public static final SetupState MARIADB_USER = new SetupStateString("MARIADB_USER", "autumn.setup.storage.mariadb.user");
    public static final SetupState MARIADB_PASSWORD = new SetupStateString("MARIADB_PASSWORD", "autumn.setup.storage.mariadb.password");
    public static final SetupState DATABASE = new SetupStateString("DATABASE", "autumn.setup.storage.database");
    public static final SetupState SQLITE_FILE_PATH = new SetupStateFile("SQLITE_FILE_PATH", "autumn.setup.storage.sqlite.file.path");
    public static final SetupState FILE_SYSTEM_STORAGE_DIRECTORY = new SetupStateFile("FILE_SYSTEM_STORAGE_DIRECTORY", "autumn.setup.storage.file.system.directory");

    public static SetupState[] values() {
        return new SetupState[]{CONSOLE_LANGUAGE, STORAGE, MONGODB_URI, MARIADB_HOST, MARIADB_PORT, MARIADB_USER, MARIADB_PASSWORD, DATABASE, SQLITE_FILE_PATH, FILE_SYSTEM_STORAGE_DIRECTORY};
    }
}
