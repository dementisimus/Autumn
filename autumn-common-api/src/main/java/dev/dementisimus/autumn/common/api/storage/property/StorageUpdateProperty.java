/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.storage.property;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents data (key and value + their name and value) for storage operations
 *
 * @since 1.0.0
 */
public interface StorageUpdateProperty extends StorageProperty {

    /**
     * Sets the storage property name and value
     *
     * @param name the storage property name
     * @param value the storage property value
     *
     * @return the storage update property object
     *
     * @since 1.0.0
     */
    @NotNull StorageUpdateProperty value(@Nullable String name, @Nullable Object value);

    /**
     * Gets the storage update property name
     *
     * @return the storage update property name
     *
     * @since 1.0.0
     */
    @Nullable String name();

    /**
     * Gets the storage update property value
     *
     * @return the storage update property value
     *
     * @since 1.0.0
     */
    @Nullable Object value();

    /**
     * Transforms {@link #name()} and {@link #value()} into a bson document
     *
     * @return {@link Bson} bson document
     *
     * @since 1.0.0
     */
    @NotNull Document document();

    /**
     * Transforms {@link #fieldName()} + {@link #fieldValue()} and {@link #name()} + {@link #value()} into a bson
     * document
     *
     * @return {@link Bson} document
     *
     * @since 1.0.0
     */
    @NotNull Document fullDocument();
}
