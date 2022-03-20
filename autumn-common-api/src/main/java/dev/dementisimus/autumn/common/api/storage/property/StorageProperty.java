/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.storage.property;

import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

/**
 * Represents data (key and value) for storage operations
 *
 * @since 1.0.0
 */
public interface StorageProperty {

    /**
     * Transforms the storage property into a string
     *
     * @return storage property as string
     *
     * @since 1.1.1
     */
    @NotNull String toString();

    /**
     * Gets the storage property field name
     *
     * @return storage property field name
     *
     * @since 1.0.0
     */
    @NotNull String fieldName();

    /**
     * Gets the storage property field value
     *
     * @return storage property field value
     *
     * @since 1.0.0
     */
    @NotNull Object fieldValue();

    /**
     * Transforms {@link #fieldName()} and {@link #fieldValue()} into a bson filter
     *
     * @return {@link Bson} bson filter
     *
     * @since 1.0.0
     */
    @NotNull Bson filter();
}
