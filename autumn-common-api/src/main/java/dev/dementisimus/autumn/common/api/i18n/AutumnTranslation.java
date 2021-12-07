/*
 | Copyright 2021 dementisimus,
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
     * @param translationProperty translation property for the translation
     *
     * @since 1.0.0
     */
    void translationProperty(@NotNull String translationProperty);

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
    @NotNull AutumnTranslation replacement(@NotNull String target, @NotNull String replacement);

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
