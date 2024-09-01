/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.i18n;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Allows the generation of message translations
 *
 * @since 1.0.0
 */
public interface Translation {

    /**
     * Sets the property used for the translation
     *
     * @param property translation property for the translation
     * @return the current {@link Translation} object
     * @since 1.0.0
     */
    Translation property(String property);

    /**
     * True, if the string should be parsed for console usage, false otherwise
     *
     * @param parseConsoleColorCodes parseConsoleColorCodes
     * @since 1.1.1
     */
    void parseConsoleColorCodes(boolean parseConsoleColorCodes);

    /**
     * Replaces a key by the given argument in the translation
     *
     * @param key      A string which will be searched for in the translation
     * @param argument If the key was found, the argument for the key
     * @return the current {@link Translation} object
     * @since 1.0.0
     */
    @NotNull Translation argument(String key, Object argument);

    /**
     * Replaces a key by the given argument translation in the translation
     *
     * @param key      A string which will be searched for in the translation
     * @param argument If the key was found, the argument translation for the key
     * @return the current {@link Translation} object
     * @since 1.1.1
     */
    @NotNull Translation argument(String key, Translation argument);

    /**
     * Replaces a key by the given argument in the translation
     *
     * @param translationArguments {@link TranslationArgument}-Array which will be searched for and replaced
     * @return the current {@link Translation} object
     * @since 1.0.0
     */
    @NotNull Translation argument(TranslationArgument... translationArguments);

    /**
     * Replaces the key with a singular or plural form, matching the given number
     *
     * @param key              key
     * @param number           number
     * @param singularArgument singular argument
     * @param pluralArgument   plural argument
     * @return the current {@link Translation} object
     * @since 1.1.1
     */
    @NotNull Translation numericalArgument(String key, int number, String singularArgument, String pluralArgument);

    /**
     * Replaces the key with a singular or plural form, matching the given number
     *
     * @param key              key
     * @param number           number
     * @param singularArgument singular argument
     * @param pluralArgument   plural argument
     * @return the current {@link Translation} object
     * @since 1.1.1
     */
    @NotNull Translation numericalArgument(String key, int number, Translation singularArgument, Translation pluralArgument);

    /**
     * Gets the translation for a locale
     *
     * @param locale translation locale
     * @return translation in given locale
     * @since 1.0.0
     */
    @NotNull String get(Locale locale);

    /**
     * Gets the translation for an {@link AutumnLanguage}
     *
     * @param autumnLanguage translation language
     * @return translation in given locale
     * @since 1.0.0
     */
    @NotNull String get(AutumnLanguage autumnLanguage);

    /**
     * Checks if a string (may be already translated) matches an translation
     *
     * @param string string
     * @return true if a match was made, false otherwise
     * @since 1.0.0
     */
    boolean matches(String string);
}
