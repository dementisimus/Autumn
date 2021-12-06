package dev.dementisimus.autumn.common.i18n;

import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultTranslationReplacement @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:22:33
 */
@NoArgsConstructor
@AllArgsConstructor
public class DefaultTranslationReplacement implements AutumnTranslationReplacement {

    private String target;
    private String replacement;

    @Override
    public void target(String target) {
        this.target = target;
    }

    @Override
    public String target() {
        return this.target;
    }

    @Override
    public void replacement(String replacement) {
        this.replacement = replacement;
    }

    @Override
    public String replacement() {
        return this.replacement;
    }

    public static AutumnTranslationReplacement of(String target, String replacement) {
        return new DefaultTranslationReplacement(target, replacement);
    }
}
