package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;

import java.util.Locale;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupStateLanguageType @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:32
 */
public class SetupStateLanguageType extends DefaultSetupState {

    public SetupStateLanguageType(String name, String messageTranslationProperty, AutumnLanguage value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateLanguageType(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, AutumnLanguage.ENGLISH);
    }

    public static AutumnLanguage transform(String string) {
        return AutumnLanguage.fromLocale(Locale.forLanguageTag(string));
    }
}
