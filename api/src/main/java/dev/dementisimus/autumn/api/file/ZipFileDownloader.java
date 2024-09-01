/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.file;

import dev.dementisimus.autumn.api.callback.SingleCallback;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Represents a downloader for zip files
 *
 * @since 1.0.0
 */
public interface ZipFileDownloader extends FileDownloader {

    /**
     * Downloads a ZIP-File from an URL
     *
     * @param extractTo       location to extract the zip file to
     * @param url             ZIP-File URL
     * @param booleanCallback true, if the file was downloaded and extracted successfully, false otherwise
     * @since 1.0.0
     */
    void downloadZip(File extractTo, String url, SingleCallback<@NotNull Boolean> booleanCallback);
}
