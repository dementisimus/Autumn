/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.setup.state.CustomSetupState;

import java.io.File;

public class SetupStateFile extends CustomSetupState {

    public SetupStateFile(String name, String messageTranslationProperty, File value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateFile(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, new File("plugins/Autumn/configuration/"));
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
