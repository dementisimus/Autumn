package dev.dementisimus.autumn.common.setup.state.type;

import dev.dementisimus.autumn.common.api.database.Database;
import dev.dementisimus.autumn.common.helper.StringHelper;
import dev.dementisimus.autumn.common.setup.state.DefaultSetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupStateDatabaseType @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:24
 */
public class SetupStateDatabaseType extends DefaultSetupState {

    public SetupStateDatabaseType(String name, String messageTranslationProperty, Database.Type value) {
        super(name, messageTranslationProperty, value);
    }

    public SetupStateDatabaseType(String name, String messageTranslationProperty) {
        super(name, messageTranslationProperty, Database.Type.MONGODB);
    }

    public static Database.Type transform(String string) {
        if(StringHelper.equalsTo(string, Database.Type.TYPES)) {
            return Database.Type.valueOf(string.toUpperCase());
        }

        return null;
    }
}
