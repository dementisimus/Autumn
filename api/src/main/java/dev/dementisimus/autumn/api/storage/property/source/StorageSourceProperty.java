/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.storage.property.source;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents a source in the provided storage
 *
 * @since 1.0.0
 */
public interface StorageSourceProperty {

    /**
     * The name of the source
     *
     * @return source name
     * @since 1.0.0
     */
    @NotNull String name();

    /**
     * The fields used by Autumn in the source
     *
     * @return source fields
     * @since 1.0.0
     */
    @NotNull Map<String, String> fields();
}
