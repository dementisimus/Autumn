/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.setup.event;

import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event called when a setup state gets deserialized
 *
 * @since 1.0.0
 */
public interface DeserializeSetupStateEvent {

    /**
     * Gets the to-be-deserialized setup state
     *
     * @return the to-be-deserialized setup state
     * @since 1.0.0
     */
    @NotNull SetupState setupState();

    /**
     * Gets the document the to-be-deserialized setup state will be deserialized from
     *
     * @return the to-be-deserialized setup state will be deserialized from
     * @since 1.0.0
     */
    @NotNull Document configuration();

    /**
     * Gets the to-be-deserialized setup state name
     *
     * @return the to-be-deserialized setup state name
     * @since 1.0.0
     */
    @NotNull String name();

    /**
     * Gets the to-be-deserialized setup state value
     *
     * @return the to-be-deserialized setup state value
     * @since 1.0.0
     */
    @NotNull Object value();

    /**
     * Sets the to-be-deserialized setup state value
     *
     * @param value the to-be-deserialized setup state value
     * @since 1.0.0
     */
    void value(@NotNull Object value);
}
