/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.storage.type.file;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import dev.dementisimus.autumn.common.api.storage.property.StorageProperty;
import dev.dementisimus.autumn.common.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.common.api.storage.type.StorageType;
import dev.dementisimus.autumn.common.configuration.CustomAutumnConfiguration;
import dev.dementisimus.autumn.common.setup.state.MainSetupStates;
import dev.dementisimus.autumn.common.storage.property.AutumnStorageUpdateProperty;
import lombok.SneakyThrows;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FileStorage implements StorageType {

    private static final String KEY = "storage-entries";

    private final File storageDirectory;

    public FileStorage() {
        this.storageDirectory = MainSetupStates.FILE_SYSTEM_STORAGE_DIRECTORY.asFile();

        if(!this.storageDirectory.exists()) {
            this.storageDirectory.mkdirs();
        }
    }

    @Override
    public void read(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull AutumnCallback<@Nullable Document> documentCallback) {
        this.list(storageSourceProperty, listDocuments -> {
            Document document = null;

            for(Document listDocument : listDocuments) {
                if(listDocument.containsKey(storageProperty.fieldName())) {
                    Object obj = listDocument.get(storageProperty.fieldName());

                    if(obj.equals(storageProperty.fieldValue())) {
                        document = listDocument;
                        break;
                    }
                }
            }

            documentCallback.done(document);
        });
    }

    @SneakyThrows
    @Override
    public void list(@NotNull StorageSourceProperty storageSourceProperty, @NotNull AutumnCallback<@NotNull List<Document>> listDocumentCallback) {
        AutumnConfiguration configuration = this.getConfiguration(storageSourceProperty);

        Document source = configuration.read();
        List<Document> documents = new ArrayList<>();

        if(source != null && source.containsKey(KEY)) {
            List<LinkedHashMap<String, Object>> rawDocuments = source.get(KEY, List.class);

            for(LinkedHashMap<String, Object> linkedHashMap : rawDocuments) {
                documents.add(new Document(linkedHashMap));
            }
        }

        listDocumentCallback.done(documents);
    }

    @Override
    public void write(@NotNull StorageSourceProperty storageSourceProperty, @NotNull Document document, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        AutumnConfiguration configuration = this.getConfiguration(storageSourceProperty);
        this.list(storageSourceProperty, listDocuments -> {
            listDocuments.add(document);

            configuration.set(KEY, listDocuments);
            configuration.write();

            booleanCallback.done(true);
        });
    }

    @Override
    public void update(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageUpdateProperty storageUpdateProperty, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        AutumnConfiguration configuration = this.getConfiguration(storageSourceProperty);
        this.list(storageSourceProperty, documents -> {

            boolean success = false;

            List<Document> updatedDocuments = new ArrayList<>();
            for(Document document : documents) {
                if(document.containsKey(storageUpdateProperty.fieldName())) {
                    Object object = document.get(storageUpdateProperty.fieldName());

                    if(object != null && object.equals(storageUpdateProperty.fieldValue())) {
                        if(storageUpdateProperty.name() != null) {
                            document.put(storageUpdateProperty.name(), storageUpdateProperty.value());
                        }else {
                            document = null;
                        }
                        success = true;
                    }
                }

                if(document != null) {
                    updatedDocuments.add(document);
                }
            }

            configuration.set(KEY, updatedDocuments);
            configuration.write();

            booleanCallback.done(success);
        });
    }

    @Override
    public void delete(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.update(storageSourceProperty, AutumnStorageUpdateProperty.of(storageProperty.fieldName(), storageProperty.fieldValue()).value(null, null), booleanCallback);
    }

    @Override
    public void isPresent(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.read(storageSourceProperty, storageProperty, document -> booleanCallback.done(document != null));
    }

    @Override
    public void close() {}

    @Override
    public @NotNull String readyTranslationProperty() {
        return "autumn.storage.file.ready";
    }

    private AutumnConfiguration getConfiguration(StorageSourceProperty storageSourceProperty) {
        Preconditions.checkNotNull(storageSourceProperty, "StorageSourceProperty may not be null!");

        return new CustomAutumnConfiguration(this.storageDirectory, storageSourceProperty.name() + ".json");
    }
}
