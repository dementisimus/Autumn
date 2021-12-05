package dev.dementisimus.autumn.common.api.i18n;

import java.util.Locale;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnTranslation @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:13:46
 */
public interface AutumnTranslation {

    void translationProperty(String translationProperty);

    AutumnTranslation replacement(String target, String replacement);

    AutumnTranslation replacement(AutumnTranslationReplacement... translationReplacements);

    String get(Locale locale);

    String get(AutumnLanguage autumnLanguage);

    boolean matches(String string);
}
