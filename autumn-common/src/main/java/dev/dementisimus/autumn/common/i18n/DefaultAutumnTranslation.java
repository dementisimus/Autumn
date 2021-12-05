package dev.dementisimus.autumn.common.i18n;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import dev.dementisimus.autumn.common.console.ConsoleColor;
import dev.dementisimus.autumn.common.i18n.property.AutumnTranslationProperty;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultAutumnTranslation @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:16:25
 */
@AllArgsConstructor
public class DefaultAutumnTranslation implements AutumnTranslation {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + "§" + "[0-9A-FK-ORX]");

    private final Map<String, String> replacements = new HashMap<>();

    private String translationProperty;

    @Override
    public void translationProperty(String translationProperty) {
        this.translationProperty = translationProperty;
    }

    @Override
    public AutumnTranslation replacement(String target, String replacement) {
        this.replacement(DefaultTranslationReplacement.of(target, replacement));
        return this;
    }

    @Override
    public AutumnTranslation replacement(AutumnTranslationReplacement... translationReplacements) {
        for(AutumnTranslationReplacement translationReplacement : translationReplacements) {
            String target = translationReplacement.target();
            String replacement = translationReplacement.replacement();

            if(!target.startsWith("$")) target = "$" + target;
            if(!target.endsWith("$")) target = target + "$";

            this.replacements.put(target, replacement);
        }

        return this;
    }

    @Override
    public String get(Locale locale) {
        return ConsoleColor.toColoredString('§', this.getMessage(locale));
    }

    @Override
    public String get(AutumnLanguage autumnLanguage) {
        return ConsoleColor.toColoredString('§', this.getMessage(autumnLanguage.getLocale()));
    }

    @Override
    public boolean matches(String string) {
        boolean matches = false;

        string = this.stripColor(string);

        for(AutumnLanguage autumnLanguage : AutumnLanguage.values()) {
            String message = this.stripColor(this.getMessage(autumnLanguage.getLocale()));

            if(string.equalsIgnoreCase(message)) {
                matches = true;
                break;
            }
        }

        return matches;
    }

    protected String getMessage(Locale locale) {
        String message = AutumnTranslationProperty.getMessage(this.translationProperty, locale);

        for(String target : this.replacements.keySet()) {
            String replacement = this.replacements.get(target);

            message = message.replace(target, replacement);
        }

        return message;
    }

    protected String stripColor(String stripFrom) {
        return STRIP_COLOR_PATTERN.matcher(stripFrom).replaceAll("");
    }
}
