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
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Set;

/**
 * Represents a downloader for files
 *
 * @since 1.0.0
 */
public interface FileDownloader {

    /**
     * Downloads files from urls
     *
     * @param folder        folder to download the files to
     * @param urls          urls
     * @param filesCallback the downloaded files
     * @since 2.0.0
     */
    void download(File folder, Set<String> urls, SingleCallback<@NotNull Set<File>> filesCallback);

    /**
     * Downloads a file from an URL
     *
     * @param folder       folder to download the file to
     * @param url          the full URL of the file
     * @param fileCallback the downloaded file
     * @since 1.0.0
     */
    default void download(File folder, String url, SingleCallback<@Nullable File> fileCallback) {
        this.download(folder, Set.of(url), files -> {
            if (files.iterator().hasNext()) {
                fileCallback.done(files.iterator().next());
            } else {
                fileCallback.done(null);
            }
        });
    }
}
