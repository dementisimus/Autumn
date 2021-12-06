/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.database;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.common.DefaultAutumn;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.database.Database;
import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import dev.dementisimus.autumn.common.api.database.type.DatabaseType;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.database.cache.DatabaseCache;
import dev.dementisimus.autumn.common.database.type.maria.MariaDatabase;
import dev.dementisimus.autumn.common.database.type.mongo.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DefaultDatabase implements Database {

    @Getter private final List<DataSourceProperty> dataSourceProperties = new ArrayList<>();
    private final AutumnTaskExecutor taskExecutor;

    @Getter @Setter private Database.Type type;
    @Getter private DatabaseType databaseType;

    private DataSourceProperty dataSourceProperty;
    private DataProperty dataProperty;
    private UpdateDataProperty updateDataProperty;
    private Document document;

    private boolean useCache = true;

    public DefaultDatabase(DefaultAutumn autumn) {
        this.taskExecutor = autumn.getTaskExecutor();
    }

    @Override
    public void generateDataSourceProperty(DataSourceProperty dataSourceProperty) {
        this.dataSourceProperties.add(dataSourceProperty);
    }

    @Override
    public void dataSourceProperty(DataSourceProperty dataSourceProperty) {
        this.dataSourceProperty = dataSourceProperty;
    }

    @Override
    public void dataProperty(DataProperty dataProperty) {
        this.dataProperty = dataProperty;
    }

    @Override
    public void updateDataProperty(UpdateDataProperty updateDataProperty) {
        this.updateDataProperty = updateDataProperty;
    }

    @Override
    public void document(Document document) {
        this.document = document;
    }

    @Override
    public void connect(AutumnCallback<Boolean> booleanCallback) {
        this.taskExecutor.asynchronous(() -> {
            switch(this.type) {
                case MONGODB -> this.databaseType = new MongoDatabase();
                default -> this.databaseType = new MariaDatabase(this);
            }
            booleanCallback.done(true);
        });
    }

    @Override
    public void disableCache() {
        this.useCache = false;
    }

    @Override
    public void read(AutumnCallback<Document> documentCallback) {
        this.dataSourcePropertyNotNull();
        this.dataPropertyNotNull();

        if(this.useCache) {
            Document document = DatabaseCache.get(this.getCacheKey());
            if(document != null) {
                documentCallback.done(document);
                return;
            }
        }

        this.taskExecutor.asynchronous(() -> {
            this.databaseType.read(this.dataSourceProperty, this.dataProperty, document -> {
                if(this.useCache && document != null && !document.isEmpty()) {
                    DatabaseCache.set(this.getCacheKey(), document);
                }
                documentCallback.done(document);
            });
        });
    }

    @Override
    public void list(AutumnCallback<List<Document>> listDocumentCallback) {
        this.dataSourcePropertyNotNull();

        if(this.useCache) {
            List<Document> documents = DatabaseCache.getList(this.getCacheKey());
            if(documents != null) {
                listDocumentCallback.done(documents);
                return;
            }
        }

        this.taskExecutor.asynchronous(() -> {
            this.databaseType.list(this.dataSourceProperty, documents -> {
                if(this.useCache && documents != null && !documents.isEmpty()) {
                    DatabaseCache.setList(this.getCacheKey(), documents);
                }
                listDocumentCallback.done(documents);
            });
        });
    }

    @Override
    public void write(AutumnCallback<Boolean> booleanCallback) {
        this.dataSourcePropertyNotNull();
        this.documentNotNull();

        this.taskExecutor.asynchronous(() -> {
            this.databaseType.write(this.dataSourceProperty, this.document, success -> {
                if(this.useCache) {
                    DatabaseCache.invalidate(this.getCacheKey());
                }
                booleanCallback.done(success);
            });
        });
    }

    @Override
    public void update(AutumnCallback<Boolean> booleanCallback) {
        this.dataSourcePropertyNotNull();
        this.updateDataPropertyNotNull();

        this.taskExecutor.asynchronous(() -> {
            this.databaseType.update(this.dataSourceProperty, this.updateDataProperty, success -> {
                if(this.useCache) {
                    DatabaseCache.invalidate(this.getCacheKey());
                }
                booleanCallback.done(success);
            });
        });
    }

    @Override
    public void delete(AutumnCallback<Boolean> booleanCallback) {
        this.dataSourcePropertyNotNull();
        this.dataPropertyNotNull();

        this.taskExecutor.asynchronous(() -> {
            this.databaseType.delete(this.dataSourceProperty, this.dataProperty, success -> {
                if(this.useCache) {
                    DatabaseCache.invalidate(this.getCacheKey());
                }
                booleanCallback.done(success);
            });
        });
    }

    @Override
    public void isPresent(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback) {
        this.taskExecutor.asynchronous(() -> this.databaseType.isPresent(dataSourceProperty, dataProperty, booleanCallback));
    }

    @Override
    public void writeOrUpdate(AutumnCallback<Boolean> booleanCallback) {
        this.documentNotNull();
        this.updateDataPropertyNotNull();

        this.isPresent(this.dataSourceProperty, this.updateDataProperty, isPresent -> {
            if(isPresent) {
                this.update(booleanCallback);
            }else {
                this.write(booleanCallback);
            }
        });
    }

    @Override
    public void close() {
        this.databaseType.close();
    }

    private String getCacheKey() {
        DataProperty dataProperty = this.dataProperty == null ? this.updateDataProperty : this.dataProperty;
        Preconditions.checkNotNull(dataProperty, "At least DataProperty or UpdateDataProperty not provided!");

        return this.dataSourceProperty.name() + "|" + dataProperty;
    }

    private void dataSourcePropertyNotNull() {
        Preconditions.checkNotNull(this.dataSourceProperty, "DataSourceProperty may not be null!");
    }

    private void dataPropertyNotNull() {
        Preconditions.checkNotNull(this.dataProperty, "DataProperty may not be null!");
    }

    private void updateDataPropertyNotNull() {
        Preconditions.checkNotNull(this.updateDataProperty, "UpdateDataProperty  may not be null!");
    }

    private void documentNotNull() {
        Preconditions.checkNotNull(this.document, "Document may not be null!");
    }
}
