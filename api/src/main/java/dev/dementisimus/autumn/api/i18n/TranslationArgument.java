/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.i18n;

/**
 * A argument for a translation
 *
 * @since 1.0.0
 */
public interface TranslationArgument {

    /**
     * Sets the argument key
     *
     * @param key the argument key
     * @since 1.0.0
     */
    void key(String key);

    /**
     * Gets the argument key
     *
     * @return the argument key
     * @since 1.0.0
     */
    String key();

    /**
     * Sets the argument value
     *
     * @param argument the argument value
     * @since 1.0.0
     */
    void argument(String argument);

    /**
     * Gets the argument value
     *
     * @return the argument value
     * @since 1.0.0
     */
    String argument();
}
