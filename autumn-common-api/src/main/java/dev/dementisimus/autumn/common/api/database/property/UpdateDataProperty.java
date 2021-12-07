/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.database.property;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

/**
 * Represents data (key and value + their name and value) for storage operations
 *
 * @since 1.0.0
 */
public interface UpdateDataProperty extends DataProperty {

    /**
     * Sets the data property name and value
     *
     * @param name the data property name
     * @param value the data property value
     *
     * @return the update data property object
     *
     * @since 1.0.0
     */
    @NotNull UpdateDataProperty value(@NotNull String name, @NotNull Object value);

    /**
     * Gets the data property name
     *
     * @return data property name
     *
     * @since 1.0.0
     */
    @NotNull String name();

    /**
     * Gets the data property value
     *
     * @return data property value
     *
     * @since 1.0.0
     */
    @NotNull Object value();

    /**
     * Transforms {@link #name()} and {@link #value()} into a bson document
     *
     * @return {@link Bson} bson document
     *
     * @since 1.0.0
     */
    @NotNull Document document();

    /**
     * Transforms {@link #fieldName()} + {@link #fieldValue()} and {@link #name()} + {@link #value()} into a bson document
     *
     * @return {@link Bson} bson document
     *
     * @since 1.0.0
     */
    @NotNull Document fullDocument();
}
