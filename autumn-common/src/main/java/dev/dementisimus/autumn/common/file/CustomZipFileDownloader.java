/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.file;

import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.file.AutumnZipFileDownloader;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CustomZipFileDownloader extends CustomFileDownloader implements AutumnZipFileDownloader {

    private File extractTo;

    public CustomZipFileDownloader(CustomAutumn autumn, String pluginName) {
        super(autumn, pluginName);
    }

    @Override
    public void downloadZip(@NotNull String url, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        super.download(url, file -> {
            if(!file.exists()) {
                booleanCallback.done(false);
                return;
            }

            ZipFile zipFile = new ZipFile(file);
            if(zipFile.isValidZipFile()) {
                try {
                    zipFile.extractAll(this.extractTo.getAbsolutePath());
                    file.delete();

                    booleanCallback.done(true);
                    return;
                }catch(ZipException e) {
                    e.printStackTrace();
                }
            }
            booleanCallback.done(false);
        });
    }

    @Override
    public void extractTo(@NotNull File extractTo) {
        this.extractTo = extractTo;
    }
}
