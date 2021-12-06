/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;

public class SetupStateString extends DefaultSetupState {

    public SetupStateString(String name, String messageTranslationProperty, String value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateString(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, "none");
    }
}
