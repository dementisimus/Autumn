/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.helper.StringHelper;
import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;

public class SetupStateBoolean extends DefaultSetupState {

    public SetupStateBoolean(String name, String messageTranslationProperty, boolean value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateBoolean(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, false);
    }

    public static boolean transform(String string) {
        return StringHelper.equalsTo(string, "y", "n", "yes", "no");
    }
}
