package dev.dementisimus.autumn.common.file;

import dev.dementisimus.autumn.common.DefaultAutumn;
import dev.dementisimus.autumn.common.api.Autumn;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.file.AutumnZipFileDownloader;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultZipFileDownloader @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:21:18
 */
public class DefaultZipFileDownloader extends DefaultFileDownloader implements AutumnZipFileDownloader {

    private File extractTo;

    public DefaultZipFileDownloader(DefaultAutumn autumn, String pluginName) {
        super(autumn, pluginName);
    }

    @Override
    public void downloadZip(String url, AutumnCallback<Boolean> booleanCallback) {
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
    public void extractTo(File extractTo) {
        this.extractTo = extractTo;
    }
}
