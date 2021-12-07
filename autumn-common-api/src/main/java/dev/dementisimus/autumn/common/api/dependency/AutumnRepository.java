/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.dependency;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a repository
 *
 * @since 1.0.0
 */
public interface AutumnRepository {

    /**
     * Gets the repository name
     *
     * @return the repository name
     *
     * @since 1.0.0
     */
    @Nullable String getName();

    /**
     * Sets the repository name
     *
     * @param name repository name
     *
     * @since 1.0.0
     */
    void setName(@NotNull String name);

    /**
     * Gets the repository URL
     *
     * @return the repository URL
     *
     * @since 1.0.0
     */
    @Nullable String getURL();

    /**
     * Sets the repository URL
     *
     * @param url repository URL
     *
     * @since 1.0.0
     */
    void setURL(@NotNull String url);
}
