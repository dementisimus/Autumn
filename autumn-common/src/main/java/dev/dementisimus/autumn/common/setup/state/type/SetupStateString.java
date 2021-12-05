package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupStateString @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:36
 */
public class SetupStateString extends DefaultSetupState {

    public SetupStateString(String name, String messageTranslationProperty, String value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateString(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, "none");
    }
}
