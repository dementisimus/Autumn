/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.i18n;

import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.common.api.storage.type.sql.SQLTypes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

/**
 * All languages supported by Autumn
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor
public enum AutumnLanguage {

    /**
     * English (United States)
     *
     * @since 1.0.0
     */
    ENGLISH(Locale.ENGLISH, "en", "autumn.language.english", 12, "4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4"),

    /**
     * German (Germany)
     *
     * @since 1.0.0
     */
    GERMAN(Locale.GERMAN, "de", "autumn.language.german", 14, "5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f");

    @Getter private final Locale locale;
    @Getter private final String id;

    @Getter private final String translationProperty;
    @Getter private final int selectionInventorySlot;
    @Getter private final String textureId;

    /**
     * Parses a locale into a AutumnLanguage
     *
     * @param locale locale
     *
     * @return an autumn language, or null, if no one was found
     *
     * @since 1.0.0
     */
    public static @Nullable AutumnLanguage fromLocale(@NotNull Locale locale) {
        AutumnLanguage language = null;

        for(AutumnLanguage autumnLanguage : AutumnLanguage.values()) {
            if(autumnLanguage.getId().equals(locale.getLanguage())) {
                language = autumnLanguage;
                break;
            }
        }

        return language;
    }

    /**
     * The storage source property used by Autumn for storing UserLanguages
     *
     * @since 1.0.0
     */
    public static class StorageSource implements StorageSourceProperty {

        /**
         * The storage source property
         *
         * @since 1.0.0
         */
        public static final StorageSource PROPERTY = new StorageSource();

        /**
         * The user field
         *
         * @since 1.0.0
         */
        public static final String USER = "user";

        /**
         * The language field
         *
         * @since 1.0.0
         */
        public static final String LANGUAGE = "language";

        @Override
        public @NotNull String name() {
            return "languages";
        }

        @Override
        public @NotNull Map<String, String> fields() {
            return Map.ofEntries(Map.entry(USER, SQLTypes.LONGTEXT), Map.entry(LANGUAGE, SQLTypes.LONGTEXT));
        }
    }
}
