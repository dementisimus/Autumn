/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api;

import dev.dementisimus.autumn.api.command.AutumnCommand;
import dev.dementisimus.autumn.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.api.file.FileDownloader;
import dev.dementisimus.autumn.api.file.ZipFileDownloader;
import dev.dementisimus.autumn.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.api.initializer.AutumnInitializer;
import dev.dementisimus.autumn.api.log.AutumnLogger;
import dev.dementisimus.autumn.api.npc.pool.AutumnNPCPool;
import dev.dementisimus.autumn.api.setup.SetupManager;
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
     * Gets the {@link Storage}, if enabled by {@link AutumnInitializer#enableStorage(StorageSourceProperty...)}
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
