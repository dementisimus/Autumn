package dev.dementisimus.autumn.common.api.i18n;

/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnTranslationReplacement @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:22:32
 */
public interface AutumnTranslationReplacement {

    void target(String target);

    String target();

    void replacement(String replacement);

    String replacement();
}
