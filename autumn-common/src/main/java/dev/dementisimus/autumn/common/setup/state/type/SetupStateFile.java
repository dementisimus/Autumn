package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupStateBoolean @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:15
 */
public class SetupStateFile extends DefaultSetupState {

    public SetupStateFile(String name, String messageTranslationProperty, File value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateFile(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, null);
    }

    public static File transform(String string, boolean isDirectory) {
        File file = new File(string);
        if(file.exists()) {
            if(!isDirectory && file.isDirectory()) {
                file = null;
            }
        }else {
            file = null;
        }
        return file;
    }
}
