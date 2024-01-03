/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.file;

import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Represents a downloader for zip files
 *
 * @since 1.0.0
 */
public interface AutumnZipFileDownloader extends AutumnFileDownloader {

    /**
     * Downloads a ZIP-File from an URL
     *
     * @param url             ZIP-File URL
     * @param booleanCallback true, if the file was found and extracted, false otherwise
     * @since 1.0.0
     */
    void downloadZip(@NotNull String url, @NotNull AutumnSingleCallback<@NotNull Boolean> booleanCallback);

    /**
     * Where the zip file will be extracted to
     *
     * @param extractTo zip file extraction location
     * @since 1.0.0
     */
    void extractTo(@NotNull File extractTo);
}
