/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.storage;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.storage.property.StorageProperty;
import dev.dementisimus.autumn.common.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the storage
 *
 * @since 1.0.0
 */
public interface Storage {

    /**
     * Generates, if not exists, a {@link StorageSourceProperty} when the storage connection is ready, if given
     *
     * @param storageSourceProperty storage source property to be generated
     *
     * @since 1.0.0
     */
    void generateSourceProperty(@NotNull StorageSourceProperty storageSourceProperty);

    /**
     * The {@link StorageSourceProperty} used for executing operations in the storage
     *
     * @param storageSourceProperty storage source property for storage operations
     *
     * @since 1.0.0
     */
    void sourceProperty(@NotNull StorageSourceProperty storageSourceProperty);

    /**
     * Data which will be used by the storage operations
     *
     * @param storageProperty storage operation data as {@link StorageProperty}
     *
     * @since 1.0.0
     */
    void property(@NotNull StorageProperty storageProperty);

    /**
     * Data which will be used by the update storage operation
     *
     * @param storageUpdateProperty storage update data as {@link StorageUpdateProperty}
     *
     * @since 1.0.0
     */
    void updateProperty(@NotNull StorageUpdateProperty storageUpdateProperty);

    /**
     * Document saved to storage
     *
     * @param document document for storage write operation
     *
     * @since 1.0.0
     */
    void document(@NotNull Document document);

    /**
     * Connects, if needed, to the storage type
     *
     * @param booleanCallback true if connection was successful, or not needed, false otherwise
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
     * Reads from storage
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
     * Checks the availability of a {@link StorageProperty} in storage
     *
     * @param booleanCallback A callback with value=true if an entry with fieldName + fieldValue as {@link StorageProperty} exists, false otherwise
     *
     * @since 1.0.0
     */
    void isPresent(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Writes, if field does not exist, to storage, else updates the provided field
     *
     * @param booleanCallback A callback with value=true if the operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void writeOrUpdate(@NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Closes the storage connection, if needed
     *
     * @since 1.0.0
     */
    void close();

    enum Type {

        /**
         * MongoDB Storage
         *
         * @since 1.0.0
         */
        MONGODB,

        /**
         * MariaDB Storage
         *
         * @since 1.0.0
         */
        MARIADB,

        /**
         * SQLite Storage
         *
         * @since 1.0.0
         */
        SQLITE,

        /**
         * File system Storage
         *
         * @since 1.1.0
         */
        FILESYSTEM;

        public static final String[] TYPES = {MONGODB.name(), MARIADB.name(), SQLITE.name(), FILESYSTEM.name()};
    }
}