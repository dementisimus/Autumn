/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.log;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import org.jetbrains.annotations.NotNull;

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
    void info(@NotNull String info);

    /**
     * Prints a translated info message to the console
     *
     * @param infoTranslation info translation
     *
     * @since 1.1.1
     */
    void info(@NotNull AutumnTranslation infoTranslation);

    /**
     * Prints a warning to the console
     *
     * @param warning warning message
     *
     * @since 1.0.0
     */
    void warning(@NotNull String warning);

    /**
     * Prints a translated warning message to the console
     *
     * @param warningTranslation warning translation
     *
     * @since 1.1.1
     */
    void warning(@NotNull AutumnTranslation warningTranslation);

    /**
     * Prints an error to the console
     *
     * @param error error message
     *
     * @since 1.0.0
     */
    void error(@NotNull String error);

    /**
     * Prints a translated error message to the console
     *
     * @param errorTranslation error translation
     *
     * @since 1.1.1
     */
    void error(@NotNull AutumnTranslation errorTranslation);

    /**
     * Sets the language used for translated messages sent to the console
     *
     * @param consoleLanguage the console language
     *
     * @since 1.1.1
     */
    void consoleLanguage(AutumnLanguage consoleLanguage);

    /**
     * Sets the plugin name
     *
     * @param pluginName plugin name
     *
     * @since 1.1.1
     */
    void pluginName(String pluginName);

    /**
     * Sets the plugin version
     *
     * @param pluginVersion plugin version
     *
     * @since 1.1.1
     */
    void pluginVersion(String pluginVersion);

    /**
     * Sets the plugin prefix
     *
     * @param pluginPrefix plugin prefix
     *
     * @since 1.1.1
     */
    void pluginPrefix(String pluginPrefix);
}
