/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.i18n;

import dev.dementisimus.autumn.api.i18n.TranslationArgument;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CustomTranslationArgument implements TranslationArgument {

    private String key;
    private String argument;

    public static TranslationArgument of(String key, String argument) {
        return new CustomTranslationArgument(key, argument);
    }

    @Override
    public void key(String key) {
        this.key = key;
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public void argument(String argument) {
        this.argument = argument;
    }

    @Override
    public String argument() {
        return this.argument;
    }
}
