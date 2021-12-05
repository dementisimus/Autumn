package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.helper.StringHelper;
import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupStateBoolean @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:15
 */
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
