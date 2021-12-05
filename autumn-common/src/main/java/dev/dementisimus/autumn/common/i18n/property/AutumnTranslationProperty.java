package dev.dementisimus.autumn.common.i18n.property;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnTranslationProperty @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:16:59
 */
public class AutumnTranslationProperty {

    private static final List<String> PROPERTY_SOURCES = new ArrayList<>();
    private static final Map<String, Properties> PROPERTIES = new HashMap<>();

    public static String getMessage(String translationProperty, Locale locale) {
        if(locale == null) locale = Locale.ENGLISH;

        String message = "[!] Language '" + locale + "' has not been registered!";

        for(String propertySource : PROPERTY_SOURCES) {
            String propertySourceMessage = getPropertySourceMessage(translationProperty, propertySource, locale);

            if(propertySource != null) {
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
            String resourceName = "lang/" + sourceName + "_" + autumnLanguage.getLocale().getLanguage() + ".properties";
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
