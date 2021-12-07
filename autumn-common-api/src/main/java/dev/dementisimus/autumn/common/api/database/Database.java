/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.database;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the database
 *
 * @since 1.0.0
 */
public interface Database {

    /**
     * Generates the {@link DataSourceProperty} when storage is ready, if not exists
     *
     * @param dataSourceProperty data source property to be generated
     *
     * @since 1.0.0
     */
    void generateDataSourceProperty(@NotNull DataSourceProperty dataSourceProperty);

    /**
     * The {@link DataSourceProperty} used for executing operations in the storage
     *
     * @param dataSourceProperty data source property for storage operations
     *
     * @since 1.0.0
     */
    void dataSourceProperty(@NotNull DataSourceProperty dataSourceProperty);

    /**
     * Data which will be used by the storage operations
     *
     * @param dataProperty storage operation data as {@link DataProperty}
     *
     * @since 1.0.0
     */
    void dataProperty(@NotNull DataProperty dataProperty);

    /**
     * Data which will be used by the update storage operation
     *
     * @param updateDataProperty update storage data as {@link UpdateDataProperty}
     *
     * @since 1.0.0
     */
    void updateDataProperty(@NotNull UpdateDataProperty updateDataProperty);

    /**
     * Document saved to storage
     *
     * @param document document for write storage operation
     *
     * @since 1.0.0
     */
    void document(@NotNull Document document);

    /**
     * Connects, if needed, to the storage type
     *
     * @param booleanCallback true if connection was successful, false otherwise
     *
     * @since 1.0.0
     */
    void connect(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Disables cache for <strong>any</strong> storage operation
     *
     * @since 1.0.0
     */
    void disableCache();

    /**
     * Reads from the storage
     *
     * @param documentCallback Callback for returning the received data
     *
     * @since 1.0.0
     */
    void read(@NotNull AutumnCallback<@NotNull Document> documentCallback);

    /**
     * Lists all storage entries
     *
     * @param listDocumentCallback A list callback with all storage entries
     *
     * @since 1.0.0
     */
    void list(@NotNull AutumnCallback<@NotNull List<Document>> listDocumentCallback);

    /**
     * Writes to storage
     *
     * @param booleanCallback A callback with value=true if the write operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void write(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Updates an already existing field in the storage
     *
     * @param booleanCallback A callback with value=true if the update operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void update(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Deletes from storage
     *
     * @param booleanCallback A callback with value=true if the delete operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void delete(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Checks the availability of a {@link DataProperty} in storage
     *
     * @param dataSourceProperty {@link DataSourceProperty} data source property
     * @param dataProperty fieldName + fieldValue as {@link DataProperty}
     * @param booleanCallback A callback with value=true if an entry with fieldName + fieldValue as {@link DataProperty} exists, false otherwise
     *
     * @since 1.0.0
     */
    void isPresent(@NotNull DataSourceProperty dataSourceProperty, @NotNull DataProperty dataProperty, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Writes, if field does not exist in storage, to storage, else updates field in storage
     *
     * @param booleanCallback A callback with value=true if the operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void writeOrUpdate(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Closes the storage connection
     *
     * @since 1.0.0
     */
    void close();

    enum Type {
        /**
         * MongoDB Database
         *
         * @since 1.0.0
         */
        MONGODB,
        /**
         * MariaDB Database
         *
         * @since 1.0.0
         */
        MARIADB,
        /**
         * SQLite Database
         *
         * @since 1.0.0
         */
        SQLITE;

        public static final String[] TYPES = {MONGODB.name(), MARIADB.name(), SQLITE.name()};
    }
}
