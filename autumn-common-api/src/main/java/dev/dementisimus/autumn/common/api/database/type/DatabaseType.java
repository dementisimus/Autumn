package dev.dementisimus.autumn.common.api.database.type;

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
 * Class DatabaseType @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:15:00
 */
public interface DatabaseType {

    void read(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Document> documentCallback);

    void list(DataSourceProperty dataSourceProperty, AutumnCallback<List<Document>> listDocumentCallback);

    void write(DataSourceProperty dataSourceProperty, Document document, AutumnCallback<Boolean> booleanCallback);

    void update(DataSourceProperty dataSourceProperty, UpdateDataProperty updateDataProperty, AutumnCallback<Boolean> booleanCallback);

    void delete(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback);

    void isPresent(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback);

    void close();

    String readyTranslationProperty();
}
