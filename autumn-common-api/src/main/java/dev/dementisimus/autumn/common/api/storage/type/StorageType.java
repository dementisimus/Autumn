/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.storage.type;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.storage.property.StorageProperty;
import dev.dementisimus.autumn.common.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents all operations which can be executed in the storage
 *
 * @since 1.0.0
 */
public interface StorageType {

    /**
     * Reads from the storage
     *
     * @param storageSourceProperty storage source property
     * @param storageProperty fieldName + fieldValue as {@link StorageProperty}
     * @param documentCallback Callback for returning the received data
     *
     * @since 1.0.0
     */
    void read(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull AutumnCallback<@NotNull Document> documentCallback);

    /**
     * Lists all storage entries
     *
     * @param storageSourceProperty storage source property
     * @param listDocumentCallback A list callback with all storage entries as {@link Document} list
     *
     * @since 1.0.0
     */
    void list(@NotNull StorageSourceProperty storageSourceProperty, @NotNull AutumnCallback<@NotNull List<Document>> listDocumentCallback);

    /**
     * Writes to storage
     *
     * @param storageSourceProperty storage source property
     * @param document {@link Document} to write to the storage
     * @param booleanCallback A callback with value=true if the write operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void write(@NotNull StorageSourceProperty storageSourceProperty, @NotNull Document document, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Updates an already existing field in the storage
     *
     * @param storageSourceProperty storage source property
     * @param storageUpdateProperty {@link StorageUpdateProperty} used for holding the keys + values for finding the to be updated field
     * and a fieldName + fieldValue
     * @param booleanCallback A callback with value=true if the update operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void update(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageUpdateProperty storageUpdateProperty, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Deletes from storage
     *
     * @param storageSourceProperty storage source property
     * @param storageProperty fieldName + fieldValue as {@link StorageProperty}
     * @param booleanCallback A callback with value=true if the delete operation was successful, false otherwise
     *
     * @since 1.0.0
     */
    void delete(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Checks the availability of a {@link StorageProperty} in storage
     *
     * @param storageSourceProperty storage source property
     * @param storageProperty fieldName + fieldValue as {@link StorageProperty}
     * @param booleanCallback A callback with value=true if an entry with fieldName + fieldValue as {@link StorageProperty} exists, false otherwise
     *
     * @since 1.0.0
     */
    void isPresent(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull AutumnCallback<@NotNull Boolean> booleanCallback);

    /**
     * Closes the storage connection, if needed
     */
    void close();

    /**
     * Gets the translation property used for printing `$StorageType$ is now ready` to the console
     *
     * @return the storage is ready translation property
     *
     * @since 1.0.0
     */
    @NotNull String readyTranslationProperty();
}
