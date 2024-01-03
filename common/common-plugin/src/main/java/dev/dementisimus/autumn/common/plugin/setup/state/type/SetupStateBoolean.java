/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.setup.state.type;

import dev.dementisimus.autumn.common.plugin.helper.StringHelper;
import dev.dementisimus.autumn.common.plugin.setup.state.CustomSetupState;

public class SetupStateBoolean extends CustomSetupState {

    public SetupStateBoolean(String name, String messageTranslationProperty, boolean value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateBoolean(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, false);
    }

    public static boolean transform(String string) {
        boolean correctInput = StringHelper.equalsTo(string, "y", "n", "yes", "no");

        if (correctInput) correctInput = (string.equalsIgnoreCase("y") || string.equalsIgnoreCase("yes"));

        return correctInput;
    }
}
