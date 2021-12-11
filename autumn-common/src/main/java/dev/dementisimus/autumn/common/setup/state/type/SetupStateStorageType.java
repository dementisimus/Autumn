/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.helper.StringHelper;
import dev.dementisimus.autumn.common.setup.state.CustomSetupState;

public class SetupStateStorageType extends CustomSetupState {

    public SetupStateStorageType(String name, String messageTranslationProperty, Storage.Type value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateStorageType(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, Storage.Type.FILESYSTEM);
    }

    public static Storage.Type transform(String string) {
        if(StringHelper.equalsTo(string, Storage.Type.TYPES)) {
            return Storage.Type.valueOf(string.toUpperCase());
        }

        return null;
    }
}
