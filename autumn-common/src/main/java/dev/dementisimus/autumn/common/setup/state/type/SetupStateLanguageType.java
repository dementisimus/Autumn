/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;

import java.util.Locale;

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
