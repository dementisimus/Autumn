/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.helper.NumberHelper;
import dev.dementisimus.autumn.common.setup.state.CustomSetupState;

public class SetupStateInteger extends CustomSetupState {

    public SetupStateInteger(String name, String messageTranslationProperty, Integer value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateInteger(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, -1);
    }

    public static Integer transform(String string) {
        return NumberHelper.toNumber(string);
    }
}
