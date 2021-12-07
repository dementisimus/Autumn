/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.i18n;

import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
@AllArgsConstructor
public class DefaultTranslationReplacement implements AutumnTranslationReplacement {

    private String target;
    private String replacement;

    @Override
    public void target(@NotNull String target) {
        this.target = target;
    }

    @Override
    public @Nullable String target() {
        return this.target;
    }

    @Override
    public void replacement(@NotNull String replacement) {
        this.replacement = replacement;
    }

    @Override
    public @Nullable String replacement() {
        return this.replacement;
    }

    public static AutumnTranslationReplacement of(String target, String replacement) {
        return new DefaultTranslationReplacement(target, replacement);
    }
}
