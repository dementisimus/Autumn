package dev.dementisimus.autumn.common.api.file;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnZipFileDownloader @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:21:17
 */
public interface AutumnZipFileDownloader {

    void downloadZip(String url, AutumnCallback<Boolean> booleanCallback);

    void extractTo(File extractTo);
}
