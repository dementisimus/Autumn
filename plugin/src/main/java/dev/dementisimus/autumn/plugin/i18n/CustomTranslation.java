/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.i18n;

import dev.dementisimus.autumn.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.api.i18n.Translation;
import dev.dementisimus.autumn.api.i18n.TranslationArgument;
import dev.dementisimus.autumn.plugin.console.ConsoleColor;
import dev.dementisimus.autumn.plugin.i18n.property.TranslationProperty;
import dev.dementisimus.autumn.plugin.language.PlayerLanguage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomTranslation implements Translation {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + "§" + "[0-9A-FK-ORX]");

    private final Map<String, String> arguments = new HashMap<>();
    private final Map<String, Translation> translationArguments = new HashMap<>();

    private boolean parseConsoleColorCodes = true;

    private String translationProperty;

    public CustomTranslation(String translationProperty) {
        this.translationProperty = translationProperty;
    }

    @Override
    public Translation property(String property) {
        this.translationProperty = property;
        return this;
    }

    @Override
    public void parseConsoleColorCodes(boolean parseConsoleColorCodes) {
        this.parseConsoleColorCodes = parseConsoleColorCodes;
    }

    @Override
    public @NotNull Translation argument(String key, Object argument) {
        this.argument(CustomTranslationArgument.of(key, argument.toString()));
        return this;
    }

    @Override
    public @NotNull Translation argument(String key, Translation argument) {
        this.translationArguments.put(this.ensureKeySyntax(key), argument);
        return this;
    }

    @Override
    public @NotNull Translation argument(TranslationArgument... translationArguments) {
        for (TranslationArgument translationArgument : translationArguments) {
            String key = this.ensureKeySyntax(translationArgument.key());
            String argument = translationArgument.argument();

            this.arguments.put(key, argument);
        }

        return this;
    }

    @Override
    public @NotNull Translation numericalArgument(String key, int number, String singularArgument, String pluralArgument) {
        return this.argument(key, (number == 0 || number > 1) ? pluralArgument : singularArgument);
    }

    @Override
    public @NotNull Translation numericalArgument(String key, int number, Translation singularArgument, Translation pluralArgument) {
        return this.argument(key, (number == 0 || number > 1) ? pluralArgument : singularArgument);
    }

    @Override
    public @NotNull String get(Locale locale) {
        if (this.parseConsoleColorCodes) {
            return ConsoleColor.toColoredString('§', this.getMessage(locale));
        }
        return this.getMessage(locale);
    }

    @Override
    public @NotNull String get(AutumnLanguage autumnLanguage) {
        return this.get(autumnLanguage.getLocale());
    }

    public @NotNull String get(Player player) {
        return this.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }

    @Override
    public boolean matches(String string) {
        boolean matches = false;

        string = this.stripColor(string);

        for (AutumnLanguage autumnLanguage : AutumnLanguage.values()) {
            String message = this.stripColor(this.getMessage(autumnLanguage.getLocale()));

            if (string.equalsIgnoreCase(message)) {
                matches = true;
                break;
            }
        }

        return matches;
    }

    //ToDo use adventure components
    protected String getMessage(Locale locale) {
        String message = TranslationProperty.getMessage(this.translationProperty, locale);

        for (String key : this.arguments.keySet()) {
            String argument = this.arguments.get(key);

            message = message.replace(key, argument);
        }

        for (String key : this.translationArguments.keySet()) {
            Translation argument = this.translationArguments.get(key);

            message = message.replace(key, argument.get(locale));
        }

        return message;
    }

    protected String stripColor(String stripFrom) {
        return STRIP_COLOR_PATTERN.matcher(stripFrom).replaceAll("");
    }

    private String ensureKeySyntax(String key) {
        if (!key.startsWith("{{")) key = "{{" + key;
        if (!key.endsWith("}}")) key = key + "}}";

        return key;
    }
}
