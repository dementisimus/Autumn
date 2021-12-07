/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.i18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A replacement for a translation
 *
 * @since 1.0.0
 */
public interface AutumnTranslationReplacement {

    /**
     * Sets the replacement target
     *
     * @param target the replacement target
     *
     * @since 1.0.0
     */
    void target(@NotNull String target);

    /**
     * Gets the replacement target
     *
     * @return the replacement target
     *
     * @since 1.0.0
     */
    @Nullable String target();

    /**
     * Sets the replacement value
     *
     * @param replacement the replacement value
     *
     * @since 1.0.0
     */
    void replacement(@NotNull String replacement);

    /**
     * Gets the replacement value
     *
     * @return the replacement value
     *
     * @since 1.0.0
     */
    @Nullable String replacement();
}
