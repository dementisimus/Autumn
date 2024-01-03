/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.language;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.plugin.storage.property.AutumnStorageProperty;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class PlayerLanguage {

    private static final Map<UUID, Locale> PLAYER_LOCALES = new HashMap<>();

    public static void set(Storage storage, UUID uuid, String locale) {
        String language = "en";
        String country = "US";

        if (locale.contains("_")) {
            String[] splittedLocaleString = locale.split("_");

            language = splittedLocaleString[0];
            country = splittedLocaleString[1].toUpperCase();
        }

        set(storage, uuid, new Locale(language, country));
    }

    public static void set(Storage storage, UUID uuid, Locale locale) {
        if (storage != null) {
            storage.sourceProperty(AutumnLanguage.StorageSource.PROPERTY);
            storage.property(AutumnStorageProperty.of(AutumnLanguage.StorageSource.USER, uuid.toString()));

            storage.read(document -> {
                if (!document.isEmpty()) {
                    AutumnLanguage language = AutumnLanguage.valueOf(document.getString(AutumnLanguage.StorageSource.LANGUAGE));

                    PLAYER_LOCALES.put(uuid, language.getLocale());
                }
            });
        }

        PLAYER_LOCALES.put(uuid, locale);
    }

    public static void overwrite(UUID uuid, Locale locale) {
        PLAYER_LOCALES.put(uuid, locale);
    }

    public static Locale get(UUID uuid) {
        return PLAYER_LOCALES.get(uuid);
    }
}
