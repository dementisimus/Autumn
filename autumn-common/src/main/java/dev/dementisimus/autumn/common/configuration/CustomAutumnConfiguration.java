/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.configuration;

import com.github.derrop.documents.Document;
import com.github.derrop.documents.Documents;
import com.github.derrop.documents.storage.DocumentStorage;
import com.github.derrop.documents.storage.JsonDocumentStorage;
import com.github.derrop.documents.storage.YamlDocumentStorage;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;
import java.util.Properties;

public class CustomAutumnConfiguration implements AutumnConfiguration {

    private final File file;

    private DocumentStorage documentStorage;
    private Document document;

    private CustomAutumnConfiguration(File file, DocumentStorage documentStorage) {
        this.file = file;

        this.setDocumentStorage(documentStorage);
    }

    public CustomAutumnConfiguration(String pathname) {
        this(new File(pathname), Documents.jsonStorage());
    }

    public CustomAutumnConfiguration(String parent, String child) {
        this(new File(parent, child), Documents.jsonStorage());
    }

    public CustomAutumnConfiguration(File file) {
        this(file, Documents.jsonStorage());
    }

    public CustomAutumnConfiguration(File parent, String child) {
        this(new File(parent, child), Documents.jsonStorage());
    }

    public CustomAutumnConfiguration(URI uri) {
        this(new File(uri), Documents.jsonStorage());
    }

    @Override
    public @NotNull CustomAutumnConfiguration enableYaml() {
        this.setDocumentStorage(Documents.yamlStorage());
        return this;
    }

    @Override
    public @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Object object) {
        this.document.append(key, object);
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

    @Override
    public void write() {
        this.documentStorage.write(this.document, this.file);
    }

    @Override
    public @Nullable Document read() {
        return this.document;
    }

    @Override
    public void read(@NotNull AutumnCallback<@Nullable Document> callback) {
        callback.done(this.read());
    }

    private void setDocumentStorage(DocumentStorage documentStorage) {
        this.documentStorage = documentStorage;

        if(this.file.exists()) {
            if(this.documentStorage instanceof JsonDocumentStorage jsonDocumentStorage) {
                this.document = jsonDocumentStorage.read(this.file);
                return;
            }else if(this.documentStorage instanceof YamlDocumentStorage yamlDocumentStorage) {
                this.document = yamlDocumentStorage.read(this.file);
                return;
            }
        }

        this.document = Documents.newDocument();
    }
}
