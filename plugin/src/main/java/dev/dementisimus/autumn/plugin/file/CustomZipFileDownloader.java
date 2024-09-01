/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.file;

import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.file.ZipFileDownloader;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@Deprecated(since = "untested")
public class CustomZipFileDownloader extends CustomFileDownloader implements ZipFileDownloader {

    private final File folder;

    public CustomZipFileDownloader(CustomAutumn autumn, String pluginName) {
        super(autumn, pluginName);

        this.folder = new File(autumn.getDataFolder(), "temp");
    }

    @Override
    public void downloadZip(File extractTo, String url, SingleCallback<@NotNull Boolean> booleanCallback) {
        super.download(this.folder, url, file -> {
            if (!file.exists()) {
                booleanCallback.done(false);
                return;
            }

            try (ZipFile zipFile = new ZipFile(file)) {
                if (zipFile.isValidZipFile()) {
                    try {
                        zipFile.extractAll(extractTo.getAbsolutePath());
                        file.delete();

                        booleanCallback.done(true);
                        return;
                    } catch (ZipException e) {
                        super.autumn.getLogger().error(e);
                    }
                }
            } catch (IOException e) {
                super.autumn.getLogger().error(e);
            }

            booleanCallback.done(false);
        });
    }
}
