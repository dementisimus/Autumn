package dev.dementisimus.autumn.common.api.i18n;

import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import dev.dementisimus.autumn.common.api.database.sql.SQLTypes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Map;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnLanguage @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:13:50
 */
@RequiredArgsConstructor
public enum AutumnLanguage {

    ENGLISH(Locale.ENGLISH, "en", "autumn.language.english", 12, "4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4"),
    GERMAN(Locale.GERMAN, "de", "autumn.language.german", 14, "5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f");

    @Getter private final Locale locale;
    @Getter private final String id;

    @Getter private final String translationProperty;
    @Getter private final int selectionInventorySlot;
    @Getter private final String textureId;

    public static AutumnLanguage fromLocale(Locale locale) {
        AutumnLanguage language = null;

        for(AutumnLanguage autumnLanguage : AutumnLanguage.values()) {
            if(autumnLanguage.getId().equals(locale.getLanguage())) {
                language = autumnLanguage;
                break;
            }
        }

        return language;
    }

    public static class DataSource implements DataSourceProperty {

        public static final DataSource PROPERTY = new DataSource();

        public static final String USER = "user";
        public static final String LANGUAGE = "language";

        @Override
        public String name() {
            return "languages";
        }

        @Override
        public Map<String, String> fields() {
            return Map.ofEntries(Map.entry(USER, SQLTypes.LONGTEXT), Map.entry(LANGUAGE, SQLTypes.LONGTEXT));
        }
    }
}
