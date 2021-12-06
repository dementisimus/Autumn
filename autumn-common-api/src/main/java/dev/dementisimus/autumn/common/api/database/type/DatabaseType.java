/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.database.type;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import org.bson.Document;

import java.util.List;

/**
 * Represents all operations which can be executed in a storage type
 *
 * @since 1.0.0
 */
public interface DatabaseType {

    /**
     * Reads from the storage
     *
     * @param dataSourceProperty {@link DataSourceProperty} data source property
     * @param dataProperty fieldName + fieldValue as {@link DataProperty}
     * @param documentCallback Callback for returning the received data
     *
     * @since 1.0.0
     */
    void read(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Document> documentCallback);

    /**
     * Lists all storage entries
     *
     * @param dataSourceProperty {@link DataSourceProperty} data source property
     * @param listDocumentCallback A list callback with all storage entries
     *
     * @since 1.0.0
     */
    void list(DataSourceProperty dataSourceProperty, AutumnCallback<List<Document>> listDocumentCallback);

    /**
     * Writes to storage
     *
     * @param dataSourceProperty {@link DataSourceProperty} data source property
     * @param document {@link Document} used for holding data
     * @param booleanCallback A callback with value=true if the write operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void write(DataSourceProperty dataSourceProperty, Document document, AutumnCallback<Boolean> booleanCallback);

    /**
     * Updates an already existing field in the storage
     *
     * @param dataSourceProperty {@link DataSourceProperty} data source property
     * @param updateDataProperty {@link UpdateDataProperty} used for holding the keys + values for finding the to be updated field
     * and a fieldName + fieldValue
     * @param booleanCallback A callback with value=true if the update operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void update(DataSourceProperty dataSourceProperty, UpdateDataProperty updateDataProperty, AutumnCallback<Boolean> booleanCallback);

    /**
     * Deletes from storage
     *
     * @param dataSourceProperty {@link DataSourceProperty} data source property
     * @param dataProperty fieldName + fieldValue as {@link DataProperty}
     * @param booleanCallback A callback with value=true if the delete operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void delete(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback);

    /**
     * Checks the availability of a {@link DataProperty} in storage
     *
     * @param dataSourceProperty {@link DataSourceProperty} data source property
     * @param dataProperty fieldName + fieldValue as {@link DataProperty}
     * @param booleanCallback A callback with value=true if an entry with fieldName + fieldValue as {@link DataProperty} exists, false otherwise
     *
     * @since 1.0.0
     */
    void isPresent(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback);

    /**
     * Closes the storage connection
     */
    void close();

    /**
     * Gets the translation property used for printing `$StorageType$ is now ready` to the console
     *
     * @return the storage is ready translation property
     *
     * @since 1.0.0
     */
    String readyTranslationProperty();
}
