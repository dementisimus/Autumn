/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.file;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.dependency.AutumnDependency;
import dev.dementisimus.autumn.common.api.file.AutumnFileDownloader;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class CustomFileDownloader implements AutumnFileDownloader {

    private final CustomAutumn autumn;
    private final String pluginName;

    private File downloadTo;

    public CustomFileDownloader(CustomAutumn autumn, String pluginName) {
        this.autumn = autumn;
        this.pluginName = pluginName;
    }

    @Override
    public void download(@NotNull String url, @NotNull AutumnCallback<@Nullable File> fileCallback) {
        Preconditions.checkNotNull(url, "URL may not be null!");
        Preconditions.checkNotNull(this.downloadTo, "Destination for downloaded file may not be null!");

        if(!this.downloadTo.exists()) {
            if(!this.downloadTo.getParentFile().exists()) this.downloadTo.getParentFile().mkdirs();

            if(this.downloadTo.getAbsolutePath().endsWith("/")) {
                this.downloadTo.mkdir();
            }

            AutumnTranslation translation = new CustomAutumnTranslation("autumn.file.download.begin");
            translation.replacement("file", url.substring(url.lastIndexOf("/") + 1));

            this.autumn.logging().info(translation);

            this.autumn.taskExecutor().asynchronous(() -> {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();

                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    httpURLConnection.setDoOutput(false);
                    httpURLConnection.setUseCaches(false);

                    httpURLConnection.connect();

                    try(InputStream inputStream = httpURLConnection.getInputStream()) {
                        Files.copy(inputStream, this.downloadTo.toPath());
                    }

                    translation.property("autumn.file.download.done");
                    this.autumn.logging().info(translation);
                    fileCallback.done(this.downloadTo);
                }catch(IOException e) {
                    e.printStackTrace();
                }
            });
        }else {
            fileCallback.done(this.downloadTo);
        }
    }

    @Override
    public void download(@NotNull AutumnDependency dependency, @NotNull AutumnCallback<@Nullable File> fileCallback) {
        Preconditions.checkNotNull(dependency, "Dependency may not be null!");
        Preconditions.checkNotNull(dependency.repository(), "Dependency-Repository may not be null!");

        if(!this.downloadTo.getAbsolutePath().endsWith(dependency.fileName())) {
            this.downloadTo = new File(this.downloadTo, dependency.fileName());
        }

        this.download(dependency.toURL(), fileCallback);
    }

    @Override
    public File downloadTo() {
        return this.downloadTo;
    }

    @Override
    public void downloadTo(@NotNull File downloadTo) {
        this.downloadTo = downloadTo;
    }

    @Override
    public void downloadTo(@NotNull String downloadTo) {
        this.downloadTo = new File(downloadTo);
    }
}
