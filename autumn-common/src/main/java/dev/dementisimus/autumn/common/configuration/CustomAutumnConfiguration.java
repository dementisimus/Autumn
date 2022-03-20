/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import lombok.SneakyThrows;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.util.Properties;

public class CustomAutumnConfiguration implements AutumnConfiguration {

    private final File file;
    private final ObjectMapper objectMapper;

    private Document document = new Document();

    @SneakyThrows
    public CustomAutumnConfiguration(File file) {
        this.file = file;
        this.objectMapper = new ObjectMapper();

        if(this.file.exists()) {
            this.document = this.objectMapper.readValue(this.file, Document.class);
        }
    }

    public CustomAutumnConfiguration(String pathname) {
        this(new File(pathname));
    }

    public CustomAutumnConfiguration(String parent, String child) {
        this(new File(parent, child));
    }

    public CustomAutumnConfiguration(File parent, String child) {
        this(new File(parent, child));
    }

    public CustomAutumnConfiguration(URI uri) {
        this(new File(uri));
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Object object) {
        this.document.append(key, object);
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull Document document) {
        for(String key : document.keySet()) {
            this.document.append(key, document.get(key));
        }
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Number number) {
        this.document.append(key, number);
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Boolean bool) {
        this.document.append(key, bool);
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, @NotNull String string) {
        this.document.append(key, string);
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Character character) {
        this.document.append(key, character);
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Properties properties) {
        this.document.append(key, properties);
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, byte[] bytes) {
        this.document.append(key, bytes);
        return this;
    }

    @SneakyThrows
    @Override
    public void write() {
        ObjectWriter objectWriter = this.objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(this.file, this.document);
    }

    @Override
    public @NotNull Document read() {
        return this.document;
    }

    @Override
    public void read(@NotNull AutumnCallback<@NotNull Document> callback) {
        callback.done(this.read());
    }
}
