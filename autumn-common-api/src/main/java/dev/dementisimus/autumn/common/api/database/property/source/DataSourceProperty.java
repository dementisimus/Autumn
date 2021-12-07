/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.database.property.source;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a data source in the provided storage
 *
 * @since 1.0.0
 */
public interface DataSourceProperty {

    /**
     * The name of the data source
     *
     * @return data source name
     *
     * @since 1.0.0
     */
    @NotNull String name();

    /**
     * The fields used by Autumn in the data source
     *
     * - Only used by MariaDB/SQLite
     *
     * @return data source fields
     *
     * @since 1.0.0
     */
    @NotNull Map<String, String> fields();
}
