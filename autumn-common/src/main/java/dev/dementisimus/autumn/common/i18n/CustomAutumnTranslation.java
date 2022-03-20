/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.i18n;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import dev.dementisimus.autumn.common.console.ConsoleColor;
import dev.dementisimus.autumn.common.i18n.property.AutumnTranslationProperty;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomAutumnTranslation implements AutumnTranslation {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + "§" + "[0-9A-FK-ORX]");

    private final Map<String, String> replacements = new HashMap<>();
    private final Map<String, AutumnTranslation> translationReplacements = new HashMap<>();

    private boolean parseConsoleColorCodes = true;

    private String translationProperty;

    public CustomAutumnTranslation(String translationProperty) {
        this.translationProperty = translationProperty;
    }

    @Override
    public void property(@NotNull String property) {
        this.translationProperty = property;
    }

    @Override
    public void parseConsoleColorCodes(boolean parseConsoleColorCodes) {
        this.parseConsoleColorCodes = parseConsoleColorCodes;
    }

    @Override
    public @NotNull AutumnTranslation replacement(@NotNull String target, @NotNull Object replacement) {
        this.replacement(CustomTranslationReplacement.of(target, replacement.toString()));
        return this;
    }

    @Override
    public @NotNull AutumnTranslation replacement(@NotNull String target, @NotNull AutumnTranslation replacement) {
        this.translationReplacements.put(this.ensureTargetSyntax(target), replacement);
        return this;
    }

    @Override
    public @NotNull AutumnTranslation replacement(@NotNull AutumnTranslationReplacement... translationReplacements) {
        for(AutumnTranslationReplacement translationReplacement : translationReplacements) {
            String target = this.ensureTargetSyntax(translationReplacement.target());
            String replacement = translationReplacement.replacement();

            this.replacements.put(target, replacement);
        }

        return this;
    }

    @Override
    public @NotNull AutumnTranslation numericalReplacement(@NotNull String target, int number, @NotNull String singularReplacement, @NotNull String pluralReplacement) {
        return this.replacement(target, (number == 0 || number > 1) ? pluralReplacement : singularReplacement);
    }

    @Override
    public @NotNull AutumnTranslation numericalReplacement(@NotNull String target, int number, @NotNull AutumnTranslation singularReplacement, @NotNull AutumnTranslation pluralReplacement) {
        return this.replacement(target, (number == 0 || number > 1) ? pluralReplacement : singularReplacement);
    }

    @Override
    public @NotNull String get(@NotNull Locale locale) {
        if(this.parseConsoleColorCodes) {
            return ConsoleColor.toColoredString('§', this.getMessage(locale));
        }
        return this.getMessage(locale);
    }

    @Override
    public @NotNull String get(@NotNull AutumnLanguage autumnLanguage) {
        return this.get(autumnLanguage.getLocale());
    }

    @Override
    public boolean matches(@NotNull String string) {
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

        for(String target : this.translationReplacements.keySet()) {
            AutumnTranslation translation = this.translationReplacements.get(target);

            message = message.replace(target, translation.get(locale));
        }

        return message;
    }

    protected String stripColor(String stripFrom) {
        return STRIP_COLOR_PATTERN.matcher(stripFrom).replaceAll("");
    }

    private String ensureTargetSyntax(String target) {
        if(!target.startsWith("$")) target = "$" + target;
        if(!target.endsWith("$")) target = target + "$";

        return target;
    }
}
