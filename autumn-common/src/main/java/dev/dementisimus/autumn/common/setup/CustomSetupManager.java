/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup;

import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.event.DeserializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.NextExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.SerializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.ValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.configuration.CustomAutumnConfiguration;
import dev.dementisimus.autumn.common.executor.CustomAutumnExecutor;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.setup.state.type.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static dev.dementisimus.autumn.common.setup.state.MainSetupStates.*;

public abstract class CustomSetupManager implements SetupManager {

    private final List<SetupState> mainSetupStates = new ArrayList<>();
    private final List<SetupState> extraSetupStates = new ArrayList<>();
    private final CustomAutumn autumn;

    @Getter boolean completed;
    @Getter private SetupState currentSetupState;

    public CustomSetupManager(CustomAutumn autumn) {
        this.autumn = autumn;
    }

    public abstract ValidateCurrentExtraSetupStateEvent callValidateCurrentSetupStateEvent(SetupState currentSetupState, String consoleInput, boolean validInput);

    protected abstract DeserializeSetupStateEvent callDeserializeSetupStateEvent(SetupState setupState, Document configuration, String name, Object value);

    protected abstract NextExtraSetupStateEvent callNextSetupStateEvent(SetupState currentSetupState, int currentStateListIndex, SetupState nextSetupState, boolean cancelled);

    protected abstract SerializeSetupStateEvent callSerializeSetupStateEvent(SetupState setupState, Object value);

    @Override
    public void mainSetupState(@NotNull SetupState setupState) {
        this.mainSetupStates.add(setupState);
    }

    @Override
    public void extraSetupState(@NotNull SetupState setupState) {
        this.extraSetupStates.add(setupState);
    }

    @Override
    public @Nullable SetupState currentSetupState() {
        return this.currentSetupState;
    }

    @SneakyThrows
    @Override
    public void currentSetupState(@NotNull SetupState setupState) {
        if(!this.completed) {
            this.currentSetupState = setupState;

            this.printSetupStateInstructions(setupState);
        }
    }

    @Override
    public void printSetupStateInstructions(@NotNull SetupState setupState) {
        AutumnTranslation translation = new CustomAutumnTranslation(setupState.messageTranslationProperty());
        translation.replacement("plugin", this.autumn.getPluginName());

        this.autumn.logging().info(translation.get(this.autumn.getDefaultLanguage()));
    }

    @Override
    public void updateCurrentSetupState(@NotNull Object value) {
        this.currentSetupState.value(value);

        if(!this.isExtraState(this.currentSetupState)) {
            int listIndex = this.mainSetupStates.indexOf(this.currentSetupState);

            if(listIndex == (this.mainSetupStates.size() - 1)) {
                if(!this.extraSetupStates.isEmpty()) {
                    this.currentSetupState(this.extraSetupStates.get(0));
                }else {
                    this.complete(true);
                }
            }else {
                SetupState nextSetupState = this.mainSetupStates.get(listIndex + 1);

                if(this.currentSetupState.equals(STORAGE)) {
                    Storage.Type storageType = this.currentSetupState.value(Storage.Type.class);

                    switch(storageType) {
                        case MONGODB -> nextSetupState = MONGODB_URI;
                        case MARIADB -> nextSetupState = MARIADB_HOST;
                        case SQLITE -> nextSetupState = SQLITE_FILE_PATH;
                        case FILESYSTEM -> nextSetupState = FILE_SYSTEM_STORAGE_DIRECTORY;
                    }
                }else if(this.currentSetupState.equals(MONGODB_URI)) {
                    nextSetupState = DATABASE;
                }else if(this.currentSetupState.equals(DATABASE) || this.currentSetupState.equals(SQLITE_FILE_PATH) || this.currentSetupState.equals(FILE_SYSTEM_STORAGE_DIRECTORY)) {
                    if(!this.extraSetupStates.isEmpty()) {
                        nextSetupState = this.extraSetupStates.get(0);
                    }else {
                        this.complete(true);
                    }
                }

                this.currentSetupState(nextSetupState);
            }
        }else {
            int listIndex = this.extraSetupStates.indexOf(this.currentSetupState);

            if(listIndex == (this.extraSetupStates.size() - 1)) {
                this.complete(true);
            }else {
                SetupState nextSetupState = this.extraSetupStates.get(listIndex + 1);

                NextExtraSetupStateEvent nextExtraSetupStateEvent = this.callNextSetupStateEvent(this.currentSetupState, listIndex, nextSetupState, false);

                if(!nextExtraSetupStateEvent.cancelled()) {
                    nextSetupState = nextExtraSetupStateEvent.nextSetupState();
                }else {
                    this.complete(true);
                }

                this.currentSetupState(nextSetupState);
            }
        }
    }

    @Override
    public void begin() {
        if(this.mainSetupStates.isEmpty()) throw new IllegalStateException("Main setup states may not be empty!");

        this.completed = false;

        new CustomAutumnExecutor(1500, 1500, TimeUnit.MILLISECONDS, executor -> executor.scheduleWithFixedDelay(() -> {
            if(!this.mainSetupStates.get(0).equals(CONSOLE_LANGUAGE)) {
                this.mainSetupStates.set(0, CONSOLE_LANGUAGE);
            }

            AutumnTranslation translation = this.getSetupTranslation("autumn.setup.begin");
            this.autumn.logging().info(translation.get(this.autumn.getDefaultLanguage()));

            this.currentSetupState(CONSOLE_LANGUAGE);
            executor.cancel();
        }));
    }

    @SneakyThrows
    @Override
    public void complete(boolean postSetup) {
        this.completed = true;
        File configFile = this.autumn.getConfigurationFile();

        List<SetupState> setupStates = new ArrayList<>(this.mainSetupStates);
        setupStates.addAll(this.extraSetupStates);

        AutumnConfiguration configuration = new CustomAutumnConfiguration(configFile);

        if(postSetup) {
            AutumnTranslation translation = this.getSetupTranslation("autumn.setup.complete");
            this.autumn.logging().info(translation.get(this.autumn.getDefaultLanguage()));

            configFile.getParentFile().mkdirs();
            configFile.createNewFile();

            for(SetupState setupState : setupStates) {

                Object value = setupState.value();
                if(setupState instanceof SetupStateBoolean) {
                    value = setupState.value(Boolean.class);
                }else if(setupState instanceof SetupStateString) {
                    value = setupState.value(String.class);
                }else if(setupState instanceof SetupStateInteger) {
                    value = setupState.value(Integer.class);
                }else if(setupState instanceof SetupStateStorageType) {
                    value = setupState.value(Storage.Type.class);
                }else if(setupState instanceof SetupStateLanguageType) {
                    value = setupState.value(AutumnLanguage.class);
                }else if(setupState instanceof SetupStateFile) {
                    File file = setupState.value(File.class);

                    if(file != null) value = file.getAbsolutePath();
                }else {
                    SerializeSetupStateEvent serializeSetupStateEvent = this.callSerializeSetupStateEvent(setupState, value);
                    value = serializeSetupStateEvent.value();
                }

                configuration.set(setupState.name(), value);
            }

            configuration.write();
        }else {
            if(configFile.exists()) {
                Document document = configuration.read();

                if(document != null && !document.isEmpty()) {
                    for(SetupState setupState : setupStates) {
                        if(document.containsKey(setupState.name())) {

                            Object value = document.get(setupState.name(), Object.class);

                            if(setupState instanceof SetupStateBoolean) {
                                value = SetupStateBoolean.transform(value.toString());
                            }else if(setupState instanceof SetupStateString) {
                                value = value.toString();
                            }else if(setupState instanceof SetupStateInteger) {
                                value = document.get(setupState.name(), Integer.class);
                            }else if(setupState instanceof SetupStateStorageType) {
                                Storage.Type storageType = Storage.Type.valueOf(value.toString());

                                this.autumn.setStorageType(storageType);
                                value = storageType;
                            }else if(setupState instanceof SetupStateLanguageType) {
                                AutumnLanguage language = AutumnLanguage.valueOf(value.toString());

                                this.autumn.setDefaultLanguage(language);
                                value = language;
                            }else if(setupState instanceof SetupStateFile) {
                                value = new File(value.toString());
                            }else {
                                DeserializeSetupStateEvent deserializeSetupStateEvent = this.callDeserializeSetupStateEvent(setupState, document, setupState.name(), value);
                                value = deserializeSetupStateEvent.value();
                            }

                            setupState.value(value);
                        }else {
                            AutumnTranslation translation = new CustomAutumnTranslation("autumn.setup.config.not.complete");

                            this.autumn.logging().warning(translation.get(this.autumn.getDefaultLanguage()));
                            this.autumn.getConfigurationFile().delete();
                            this.begin();
                            break;
                        }
                    }
                }else {
                    this.begin();
                    return;
                }
            }else {
                this.begin();
                return;
            }
        }

        this.autumn.postSetupInitialization();
    }

    @Override
    public boolean isExtraState(@NotNull SetupState setupState) {
        return this.extraSetupStates.contains(setupState);
    }

    private AutumnTranslation getSetupTranslation(String translationProperty) {
        AutumnTranslation translation = new CustomAutumnTranslation(translationProperty);
        translation.replacement("plugin", this.autumn.getPluginName());

        return translation;
    }
}
