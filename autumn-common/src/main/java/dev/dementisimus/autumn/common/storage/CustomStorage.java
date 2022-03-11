/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.storage;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.api.storage.property.StorageProperty;
import dev.dementisimus.autumn.common.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.common.api.storage.type.StorageType;
import dev.dementisimus.autumn.common.storage.cache.StorageCache;
import dev.dementisimus.autumn.common.storage.type.file.FileStorage;
import dev.dementisimus.autumn.common.storage.type.maria.MariaStorage;
import dev.dementisimus.autumn.common.storage.type.mongo.MongoStorage;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomStorage implements Storage {

    @Getter private final List<StorageSourceProperty> storageSourceProperties = new ArrayList<>();
    private final AutumnTaskExecutor taskExecutor;

    @Getter @Setter private Storage.Type type;
    @Getter private StorageType storageType;

    private StorageSourceProperty storageSourceProperty;
    private StorageProperty storageProperty;
    private StorageUpdateProperty storageUpdateProperty;
    private Document document;

    private boolean useCache = true;

    public CustomStorage(CustomAutumn autumn) {
        this.taskExecutor = autumn.taskExecutor();
    }

    @Override
    public void generateSourceProperty(@NotNull StorageSourceProperty storageSourceProperty) {
        this.storageSourceProperties.add(storageSourceProperty);
    }

    @Override
    public void sourceProperty(@NotNull StorageSourceProperty storageSourceProperty) {
        this.storageSourceProperty = storageSourceProperty;
    }

    @Override
    public void property(@NotNull StorageProperty storageProperty) {
        this.storageProperty = storageProperty;
    }

    @Override
    public void updateProperty(@NotNull StorageUpdateProperty storageUpdateProperty) {
        this.storageUpdateProperty = storageUpdateProperty;
    }

    @Override
    public void document(@NotNull Document document) {
        this.document = document;
    }

    @Override
    public void connect(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.taskExecutor.asynchronous(() -> {
            switch(this.type) {
                case MONGODB -> this.storageType = new MongoStorage();
                case FILESYSTEM -> this.storageType = new FileStorage();
                default -> this.storageType = new MariaStorage(this);
            }
            booleanCallback.done(true);
        });
    }

    @Override
    public void disableCache() {
        this.useCache = false;
    }

    @Override
    public void read(@NotNull AutumnCallback<@NotNull Document> documentCallback) {
        this.storageSourcePropertyNotNull();
        this.storagePropertyNotNull();

        if(this.useCache) {
            Document document = StorageCache.get(this.getCacheKey());
            if(document != null) {
                documentCallback.done(document);
                return;
            }
        }

        this.taskExecutor.asynchronous(() -> {
            this.storageType.read(this.storageSourceProperty, this.storageProperty, document -> {
                if(this.useCache && !document.isEmpty()) {
                    StorageCache.set(this.getCacheKey(), document);
                }

                this.useCache = true;
                documentCallback.done(document);
            });
        });
    }

    @Override
    public void list(@NotNull AutumnCallback<@NotNull List<Document>> listDocumentCallback) {
        this.storageSourcePropertyNotNull();

        String listCacheKey = this.storageSourceProperty.name() + "_list";
        if(this.useCache) {
            List<Document> documents = StorageCache.getList(listCacheKey);
            if(documents != null) {
                listDocumentCallback.done(documents);
                return;
            }
        }

        this.taskExecutor.asynchronous(() -> {
            this.storageType.list(this.storageSourceProperty, documents -> {
                if(this.useCache && !documents.isEmpty()) {
                    StorageCache.setList(listCacheKey, documents);
                }

                this.useCache = true;
                listDocumentCallback.done(documents);
            });
        });
    }

    @Override
    public void write() {
        this.write(success -> {});
    }

    @Override
    public void write(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.storageSourcePropertyNotNull();
        this.documentNotNull();

        this.taskExecutor.asynchronous(() -> {
            this.storageType.write(this.storageSourceProperty, this.document, success -> {
                if(this.useCache) {
                    StorageCache.invalidate(this.getCacheKey());
                }

                this.useCache = true;
                booleanCallback.done(success);
            });
        });
    }

    @Override
    public void update() {
        this.update(success -> {});
    }

    @Override
    public void update(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.storageSourcePropertyNotNull();
        this.storageUpdatePropertyNotNull();

        this.taskExecutor.asynchronous(() -> {
            this.storageType.update(this.storageSourceProperty, this.storageUpdateProperty, success -> {
                if(this.useCache) {
                    StorageCache.invalidate(this.getCacheKey());
                }

                this.useCache = true;
                booleanCallback.done(success);
            });
        });
    }

    @Override
    public void delete() {
        this.delete(success -> {});
    }

    @Override
    public void delete(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.storageSourcePropertyNotNull();
        this.storagePropertyNotNull();

        this.taskExecutor.asynchronous(() -> {
            this.storageType.delete(this.storageSourceProperty, this.storageProperty, success -> {
                if(this.useCache) {
                    StorageCache.invalidate(this.getCacheKey());
                }

                this.useCache = true;
                booleanCallback.done(success);
            });
        });
    }

    @Override
    public void isPresent(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.taskExecutor.asynchronous(() -> this.storageType.isPresent(this.storageSourceProperty, this.storageProperty, booleanCallback));
    }

    @Override
    public void writeOrUpdate() {
        this.writeOrUpdate(success -> {});
    }

    @Override
    public void writeOrUpdate(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback) {
        this.documentNotNull();
        this.storageUpdatePropertyNotNull();

        this.isPresent(isPresent -> {
            if(isPresent) {
                this.update(booleanCallback);
            }else {
                this.write(booleanCallback);
            }
        });
    }

    @Override
    public void close() {
        this.storageType.close();
    }

    private String getCacheKey() {
        StorageProperty storageProperty = this.storageProperty == null ? this.storageUpdateProperty : this.storageProperty;
        Preconditions.checkNotNull(storageProperty, "At least StorageProperty or StorageUpdateProperty not provided!");

        return this.storageSourceProperty.name() + "|" + storageProperty;
    }

    private void storageSourcePropertyNotNull() {
        Preconditions.checkNotNull(this.storageSourceProperty, "StorageSourceProperty may not be null!");
    }

    private void storagePropertyNotNull() {
        Preconditions.checkNotNull(this.storageProperty, "StorageProperty may not be null!");
    }

    private void storageUpdatePropertyNotNull() {
        Preconditions.checkNotNull(this.storageUpdateProperty, "StorageUpdateProperty  may not be null!");
    }

    private void documentNotNull() {
        Preconditions.checkNotNull(this.document, "Document may not be null!");
    }
}
