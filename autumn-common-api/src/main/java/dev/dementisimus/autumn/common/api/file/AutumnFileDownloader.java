/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.file;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.dependency.AutumnDependency;

import java.io.File;

/**
 * Represents a downloader for files
 *
 * @since 1.0.0
 */
public interface AutumnFileDownloader {

    /**
     * Downloads a file from an URL
     *
     * @param url the full URL of the file
     * @param fileCallback the file, if it existed, null otherwise
     *
     * @since 1.0.0
     */
    void download(String url, AutumnCallback<File> fileCallback);

    /**
     * Downloads a file identified by an {@link AutumnDependency}
     *
     * @param dependency the {@link AutumnDependency} of the file
     * @param fileCallback the file, if it existed, null otherwise
     *
     * @since 1.0.0
     */
    void download(AutumnDependency dependency, AutumnCallback<File> fileCallback);

    /**
     * Gets the location where the downloaded file will be saved to
     *
     * @return the file where the downloaded file will be stored at
     *
     * @since 1.0.0
     */
    File getDownloadTo();

    /**
     * Sets the location where the downloaded file will be saved to
     *
     * @param downloadTo the file where the downloaded file will be stored at
     *
     * @since 1.0.0
     */
    void setDownloadTo(File downloadTo);
}
