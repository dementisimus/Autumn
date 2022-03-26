/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.i18n.property;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AutumnTranslationProperty {

    private static final List<String> PROPERTY_SOURCES = new ArrayList<>();
    private static final Map<String, Properties> PROPERTIES = new HashMap<>();

    public static String getMessage(String translationProperty, Locale locale) {
        if(locale == null) locale = Locale.ENGLISH;

        String message = "[!] Language '" + locale + "' has not been registered!";

        for(String propertySource : PROPERTY_SOURCES) {
            String propertySourceMessage = getPropertySourceMessage(translationProperty, propertySource, locale);

            if(propertySourceMessage != null) {
                message = propertySourceMessage;
                break;
            }else {
                message = "[!] property '" + translationProperty + "' not found for language " + locale + "!";
            }
        }

        return message;
    }

    public static void scan(Class<?> source, String sourceName) {
        if(PROPERTY_SOURCES.contains(sourceName)) return;

        for(AutumnLanguage autumnLanguage : AutumnLanguage.values()) {
            String resourceName = "i18n/" + sourceName + "_" + autumnLanguage.getLocale().getLanguage() + ".properties";
            InputStream inputStream = source.getClassLoader().getResourceAsStream(resourceName);

            if(inputStream != null) {
                Properties properties = new Properties();

                try(Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                    properties.load(reader);

                    if(!PROPERTY_SOURCES.contains(sourceName)) {
                        PROPERTY_SOURCES.add(sourceName);
                    }

                    PROPERTIES.put(getPropertyKey(sourceName, autumnLanguage.getLocale()), properties);
                }catch(IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    private static String getPropertySourceMessage(String translationProperty, String source, Locale locale) {
        Properties properties = PROPERTIES.get(getPropertyKey(source, locale));

        if(properties != null) {
            return properties.getProperty(translationProperty);
        }

        return null;
    }

    private static String getPropertyKey(String source, Locale locale) {
        return source + "_" + locale.getLanguage();
    }
}
