/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.database.property;

import org.bson.conversions.Bson;

/**
 * Represents data (key & value) for storage operations
 *
 * @since 1.0.0
 */
public interface DataProperty {

    /**
     * Gets the data property field name
     *
     * @return data property field name
     *
     * @since 1.0.0
     */
    String fieldName();

    /**
     * Gets the data property field value
     *
     * @return data property field value
     *
     * @since 1.0.0
     */
    Object fieldValue();

    /**
     * Transforms {@link #fieldName()} & {@link #fieldValue()} into a bson filter
     *
     * @return {@link Bson} bson filter
     *
     * @since 1.0.0
     */
    Bson filter();

    /**
     * Transforms the data property into a string
     *
     * @return data property as string
     *
     * @since 1.0.0
     */
    String toString();
}
