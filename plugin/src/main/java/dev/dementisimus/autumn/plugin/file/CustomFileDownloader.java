/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.file;

import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.file.FileDownloader;
import dev.dementisimus.autumn.api.i18n.Translation;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.i18n.CustomTranslation;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@Deprecated(since = "untested")
public class CustomFileDownloader implements FileDownloader {

    protected final CustomAutumn autumn;
    protected final String pluginName;

    public CustomFileDownloader(CustomAutumn autumn, String pluginName) {
        this.autumn = autumn;
        this.pluginName = pluginName;
    }

    @Override
    public void download(File folder, Set<String> urls, SingleCallback<@NotNull Set<File>> filesCallback) {
        if (!folder.exists()) {
            folder.mkdirs();
        }

        this.autumn.getTaskExecutor().asynchronous(() -> {
            Set<File> files = new HashSet<>();

            for (String url : urls) {
                Translation translation = new CustomTranslation("autumn.file.download.begin");
                String file = url.substring(url.lastIndexOf("/") + 1);

                translation.argument("plugin", this.pluginName);
                this.autumn.getLogger().info(translation.argument("file", file));
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) URI.create(url).toURL().openConnection();

                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                    httpURLConnection.setDoOutput(false);
                    httpURLConnection.setUseCaches(false);

                    httpURLConnection.connect();

                    try (InputStream inputStream = httpURLConnection.getInputStream()) {
                        Files.copy(inputStream, folder.toPath());
                        files.add(new File(folder, file));
                    }

                    this.autumn.getLogger().info(translation.property("autumn.file.download.done"));
                } catch (IOException e) {
                    this.autumn.getLogger().error(e);
                }
            }

            filesCallback.done(files);
        });
    }
}
