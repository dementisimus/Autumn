/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.value;

import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.setup.event.ValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.setup.CustomSetupManager;
import dev.dementisimus.autumn.common.setup.state.MainSetupStates;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateBoolean;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateFile;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateInteger;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateLanguageType;
import dev.dementisimus.autumn.common.setup.state.type.SetupStateStorageType;
import org.bson.Document;

public abstract class SetupValueManager {

    protected final CustomAutumn autumn;
    protected final CustomSetupManager setupManager;

    public SetupValueManager(CustomAutumn autumn, CustomSetupManager setupManager) {
        this.autumn = autumn;
        this.setupManager = setupManager;
    }

    protected void setConsoleInput(Object value) {
        SetupState setupState = this.setupManager.currentSetupState();
        Document incompleteConfiguration = this.setupManager.incompleteConfiguration();
        boolean skip = value.toString().equalsIgnoreCase("skip");

        if(setupState == null) return;

        if(skip) {
            if(incompleteConfiguration != null) {
                value = incompleteConfiguration.get(setupState.name());
            }else {
                skip = false;
            }
        }

        String stringValue = String.valueOf(value);

        if(setupState instanceof SetupStateBoolean) {
            value = SetupStateBoolean.transform(stringValue);
        }else if(setupState instanceof SetupStateStorageType) {
            Storage.Type storageType = SetupStateStorageType.transform(stringValue);

            this.autumn.setStorageType(storageType);
            value = storageType;
        }else if(setupState.equals(MainSetupStates.SQLITE_FILE_PATH)) {
            value = SetupStateFile.transform(stringValue, false);
        }else if(setupState.equals(MainSetupStates.FILE_SYSTEM_STORAGE_DIRECTORY)) {
            value = SetupStateFile.transform(stringValue, true);
        }else if(setupState instanceof SetupStateInteger) {
            value = SetupStateInteger.transform(stringValue);
        }else if(setupState instanceof SetupStateLanguageType) {
            AutumnLanguage language = SetupStateLanguageType.transform(stringValue, skip);

            this.autumn.setDefaultLanguage(language);
            value = language;
        }

        boolean validInput = true;
        if(value == null) {
            validInput = false;
        }else if(value instanceof Integer integerValue) {
            if(integerValue == -1) {
                validInput = false;
            }
        }

        if(setupState.isExtraState(this.setupManager)) {
            ValidateCurrentExtraSetupStateEvent validateCurrentExtraSetupStateEvent = this.setupManager.callValidateCurrentSetupStateEvent(setupState, stringValue, validInput);

            validInput = validateCurrentExtraSetupStateEvent.validInput();
        }

        if(validInput) {
            this.setupManager.updateCurrentSetupState(value);
        }else {
            if(skip) this.autumn.logging().warning(new CustomAutumnTranslation("autumn.setup.no.proper.config.value"));

            this.setupManager.printSetupStateInstructions(setupState);
        }
    }
}
