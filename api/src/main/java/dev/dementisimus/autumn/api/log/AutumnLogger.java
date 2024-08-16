/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.log;

import dev.dementisimus.autumn.api.i18n.Translation;

/**
 * Console message Logging
 *
 * @since 1.0.0
 */
public interface AutumnLogger {

    /**
     * Prints an info to the console
     *
     * @param info info message
     * @since 1.0.0
     */
    void info(String info);

    /**
     * Prints a translated info message to the console
     *
     * @param infoTranslation info translation
     * @since 1.1.1
     */
    void info(Translation infoTranslation);

    /**
     * Prints a warning to the console
     *
     * @param warning warning message
     * @since 1.0.0
     */
    void warning(String warning);

    /**
     * Prints a translated warning message to the console
     *
     * @param warningTranslation warning translation
     * @since 1.1.1
     */
    void warning(Translation warningTranslation);

    /**
     * Prints an error to the console
     *
     * @param error error message
     * @since 1.0.0
     */
    void error(String error);

    /**
     * Prints a translated error message to the console
     *
     * @param errorTranslation error translation
     * @since 1.1.1
     */
    void error(Translation errorTranslation);

    /**
     * Prints an exception as an error message to the console
     *
     * @param throwable throwable
     * @since 2.0.0
     */
    void error(Throwable throwable);
}
