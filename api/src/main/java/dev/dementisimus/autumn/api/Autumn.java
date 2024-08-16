/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api;

import dev.dementisimus.autumn.api.callback.EmptyCallback;
import dev.dementisimus.autumn.api.command.AutumnCommand;
import dev.dementisimus.autumn.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.api.file.FileDownloader;
import dev.dementisimus.autumn.api.file.ZipFileDownloader;
import dev.dementisimus.autumn.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.api.log.AutumnLogger;
import dev.dementisimus.autumn.api.npc.pool.AutumnNPCPool;
import dev.dementisimus.autumn.api.setup.SetupManager;
import dev.dementisimus.autumn.api.setup.state.SetupState;
import dev.dementisimus.autumn.api.storage.Storage;
import dev.dementisimus.autumn.api.storage.property.source.StorageSourceProperty;
import org.bukkit.event.Listener;

import java.io.File;

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
     * @since 1.0.0
     */
    void extraSetupStates(SetupState... setupStates);

    /**
     * Enables an extra setup state
     *
     * @param setupState extra setup state
     * @since 1.1.0
     */
    void extraSetupState(SetupState setupState);

    /**
     * Initializes Autumn and its plugin
     *
     * @param initializationCallback callback for when initialization is done
     * @since 1.0.0
     */
    void initialize(EmptyCallback initializationCallback);

    /**
     * Provides the storage functionality (needs at least {@link #storageSetupStates()}
     *
     * @param storageSourceProperties storage source properties
     * @since 1.0.0
     */
    void useStorage(StorageSourceProperty... storageSourceProperties);

    /**
     * Registers a brigadier command
     *
     * @param command command to register
     * @since 2.0.0
     */
    void registerCommand(AutumnCommand command);

    /**
     * Registers a bukkit event listener
     *
     * @param listener listener to register
     * @since 2.0.0
     */
    void registerListener(Listener listener);

    /**
     * @since 1.1.1
     */
    File getConfigurationFile();

    /**
     * @since 1.1.1
     */
    AutumnTaskExecutor getTaskExecutor();

    /**
     * @since 1.1.1
     */
    AutumnLogger getLogger();

    /**
     * @since 1.1.1
     */
    AutumnLanguage getDefaultLanguage();

    /**
     * @since 1.1.1
     */
    SetupManager getSetupManager();

    /**
     * Gets the {@link Storage}, if enabled by {@link Autumn#useStorage(StorageSourceProperty...)}
     *
     * @return the {@link Storage}, if enabled, else null
     * @since 1.1.1
     */
    Storage getStorage();

    /**
     * @since 1.1.0
     */
    FileDownloader getFileDownloader();

    /**
     * @since 1.1.1
     */
    ZipFileDownloader getZipFileDownloader();

    /**
     * @since 1.1.1
     */
    AutumnNPCPool getNpcPool();
}
