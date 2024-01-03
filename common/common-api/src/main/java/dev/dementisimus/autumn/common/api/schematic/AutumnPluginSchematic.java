/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.schematic;

import dev.dementisimus.autumn.common.api.Autumn;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.api.file.AutumnFileDownloader;
import dev.dementisimus.autumn.common.api.file.AutumnZipFileDownloader;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.injection.AutumnInjector;
import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;

import java.io.File;

/**
 * Autumn's plugin schematic
 *
 * @since 1.1.1
 */
public interface AutumnPluginSchematic {

    /**
     * Gets the {@link AutumnTaskExecutor}
     *
     * @return the {@link AutumnTaskExecutor}
     * @since 1.1.1
     */
    AutumnTaskExecutor taskExecutor();

    /**
     * Gets the {@link AutumnLogging}
     *
     * @return the {@link AutumnLogging}
     * @since 1.1.1
     */
    AutumnLogging logging();

    /**
     * Gets the {@link AutumnInjector}
     *
     * @return the {@link AutumnInjector}
     * @since 1.1.1
     */
    AutumnInjector injector();

    /**
     * Gets the default {@link AutumnLanguage}
     *
     * @return the default {@link AutumnLanguage}
     * @since 1.1.1
     */
    AutumnLanguage defaultLanguage();

    /**
     * Gets the {@link SetupManager}
     *
     * @return the {@link SetupManager}
     * @since 1.1.1
     */
    SetupManager setupManager();

    /**
     * Gets the {@link Storage}, if enabled by {@link Autumn#useStorage(StorageSourceProperty...)}
     *
     * @return the {@link Storage}, if enabled, else null
     * @since 1.1.1
     */
    Storage storage();

    /**
     * Gets the {@link AutumnFileDownloader}
     *
     * @return the {@link AutumnFileDownloader}
     * @since 1.1.0
     */
    AutumnFileDownloader fileDownloader();

    /**
     * Gets the {@link AutumnZipFileDownloader}
     *
     * @return the {@link AutumnZipFileDownloader}
     * @since 1.1.1
     */
    AutumnZipFileDownloader zipfileDownloader();

    /**
     * Gets the name of the plugin using Autumn
     *
     * @return the name of the plugin using Autumn
     * @since 1.1.1
     */
    String pluginName();

    /**
     * Gets the configuration file
     *
     * @return the configuration file
     * @since 1.1.1
     */
    File configurationFile();

    /**
     * Gets the plugin folder
     *
     * @return the plugin folder
     * @since 1.1.1
     */
    File pluginFolder();
}
