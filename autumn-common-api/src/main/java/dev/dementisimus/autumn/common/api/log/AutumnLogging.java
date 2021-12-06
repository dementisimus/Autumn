/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.log;

/**
 * Console message Logging
 *
 * @since 1.0.0
 */
public interface AutumnLogging {

    /**
     * Prints an info to the console
     *
     * @param info info message
     *
     * @since 1.0.0
     */
    void info(String info);

    /**
     * Prints a warning to the console
     *
     * @param warning warning message
     *
     * @since 1.0.0
     */
    void warning(String warning);

    /**
     * Prints an error to the console
     *
     * @param error error message
     *
     * @since 1.0.0
     */
    void error(String error);
}
