/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.setup.state;

import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.configuration.CustomAutumnConfiguration;
import dev.dementisimus.autumn.common.helper.NumberHelper;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class CustomSetupState implements SetupState {

    private final String name;
    private final String messageTranslationProperty;

    private Object value;

    public CustomSetupState(String name, String messageTranslationProperty, Object value) {
        this.name = name;
        this.messageTranslationProperty = messageTranslationProperty;
        this.value = value;
    }

    @Override
    public @Nullable Object value() {
        return this.value;
    }

    @Override
    public <T> @Nullable T value(@NotNull Class<T> clazz) {
        return (T) this.value();
    }

    @Override
    public void value(@NotNull Object value) {
        this.value = value;
    }

    @Override
    public @NotNull String messageTranslationProperty() {
        return this.messageTranslationProperty;
    }

    @Override
    public @Nullable String asString() {
        return this.value == null ? null : this.value.toString();
    }

    @Override
    public int asInteger() {
        return NumberHelper.toNumber(this.asString());
    }

    @Override
    public boolean asBoolean() {
        return Boolean.parseBoolean(this.asString());
    }

    @Override
    public @Nullable File asFile() {
        return this.asString() == null ? null : new File(this.asString());
    }

    @Override
    public boolean isPresentInConfigFile(@NotNull File configFile) {
        AutumnConfiguration configuration = new CustomAutumnConfiguration(configFile);
        Document document = configuration.read();

        return document != null && !document.isEmpty() && document.containsKey(this.name);
    }

    @Override
    public boolean isExtraState(@NotNull SetupManager setupManager) {
        return setupManager.isExtraState(this);
    }

    @Override
    public @NotNull String name() {
        return this.name;
    }
}
