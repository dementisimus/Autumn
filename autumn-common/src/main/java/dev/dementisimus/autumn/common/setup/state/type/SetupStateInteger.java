package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.helper.NumberHelper;
import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupStateInteger @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:28
 */
public class SetupStateInteger extends DefaultSetupState {

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
