/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.i18n;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Allows the generation of message translations
 *
 * @since 1.0.0
 */
public interface AutumnTranslation {

    /**
     * Sets the property used for the translation
     *
     * @param property translation property for the translation
     *
     * @return the current {@link AutumnTranslation} object
     *
     * @since 1.0.0
     */
    AutumnTranslation property(@NotNull String property);

    /**
     * True, if the string should be parsed for console usage, false otherwise
     *
     * @param parseConsoleColorCodes parseConsoleColorCodes
     *
     * @since 1.1.1
     */
    void parseConsoleColorCodes(boolean parseConsoleColorCodes);

    /**
     * Replaces a target by the given replacement in the translation
     *
     * @param target A string which will be searched for in the translation
     * @param replacement If the target was found, the replacement for the target
     *
     * @return the current {@link AutumnTranslation} object
     *
     * @since 1.0.0
     */
    @NotNull AutumnTranslation replacement(@NotNull String target, @NotNull Object replacement);

    /**
     * Replaces a target by the given replacement translation in the translation
     *
     * @param target A string which will be searched for in the translation
     * @param replacement If the target was found, the replacement translation for the target
     *
     * @return the current {@link AutumnTranslation} object
     *
     * @since 1.1.1
     */
    @NotNull AutumnTranslation replacement(@NotNull String target, @NotNull AutumnTranslation replacement);

    /**
     * Replaces a target by the given replacement in the translation
     *
     * @param translationReplacements {@link AutumnTranslationReplacement}-Array which will be searched for and replaced
     *
     * @return the current {@link AutumnTranslation} object
     *
     * @since 1.0.0
     */
    @NotNull AutumnTranslation replacement(@NotNull AutumnTranslationReplacement... translationReplacements);

    /**
     * Replaces the target with a singular or plural form, matching the given number
     *
     * @param target target
     * @param number number
     * @param singularReplacement singular replacement
     * @param pluralReplacement plural replacement
     *
     * @return the current {@link AutumnTranslation} object
     *
     * @since 1.1.1
     */
    @NotNull AutumnTranslation numericalReplacement(@NotNull String target, int number, @NotNull String singularReplacement, @NotNull String pluralReplacement);

    /**
     * Replaces the target with a singular or plural form, matching the given number
     *
     * @param target target
     * @param number number
     * @param singularReplacement singular replacement
     * @param pluralReplacement plural replacement
     *
     * @return the current {@link AutumnTranslation} object
     *
     * @since 1.1.1
     */
    @NotNull AutumnTranslation numericalReplacement(@NotNull String target, int number, @NotNull AutumnTranslation singularReplacement, @NotNull AutumnTranslation pluralReplacement);

    /**
     * Gets the translation for a locale
     *
     * @param locale translation locale
     *
     * @return translation in given locale
     *
     * @since 1.0.0
     */
    @NotNull String get(@NotNull Locale locale);

    /**
     * Gets the translation for an {@link AutumnLanguage}
     *
     * @param autumnLanguage translation language
     *
     * @return translation in given locale
     *
     * @since 1.0.0
     */
    @NotNull String get(@NotNull AutumnLanguage autumnLanguage);

    /**
     * Checks if a string (may be already translated) matches an translation
     *
     * @param string string
     *
     * @return true if a match was made, false otherwise
     *
     * @since 1.0.0
     */
    boolean matches(@NotNull String string);
}
