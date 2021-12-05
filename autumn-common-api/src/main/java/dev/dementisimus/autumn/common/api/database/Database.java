package dev.dementisimus.autumn.common.api.database;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import org.bson.Document;

import java.util.List;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class Database @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:14:59
 */
public interface Database {

    void generateDataSourceProperty(DataSourceProperty dataSourceProperty);

    void dataSourceProperty(DataSourceProperty dataSourceProperty);

    void dataProperty(DataProperty dataProperty);

    void updateDataProperty(UpdateDataProperty updateDataProperty);

    void document(Document document);

    void connect(AutumnCallback<Boolean> booleanCallback);

    void disableCache();

    void read(AutumnCallback<Document> documentCallback);

    void list(AutumnCallback<List<Document>> listDocumentCallback);

    void write(AutumnCallback<Boolean> booleanCallback);

    void update(AutumnCallback<Boolean> booleanCallback);

    void delete(AutumnCallback<Boolean> booleanCallback);

    void isPresent(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback);

    void writeOrUpdate(AutumnCallback<Boolean> booleanCallback);

    void close();

    enum Type {
        MONGODB,
        MARIADB,
        SQLITE;

        public static final String[] TYPES = {MONGODB.name(), MARIADB.name(), SQLITE.name()};
    }
}
