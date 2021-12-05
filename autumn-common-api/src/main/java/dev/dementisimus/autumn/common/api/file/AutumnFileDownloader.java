package dev.dementisimus.autumn.common.api.file;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.dependency.AutumnDependency;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnFileDownloader @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:18:59
 */
public interface AutumnFileDownloader {

    void download(String url, AutumnCallback<File> fileCallback);

    void download(AutumnDependency dependency, AutumnCallback<File> fileCallback);

    File getDownloadTo();

    void setDownloadTo(File downloadTo);
}
