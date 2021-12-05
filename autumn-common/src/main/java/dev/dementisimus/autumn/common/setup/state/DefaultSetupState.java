package dev.dementisimus.autumn.common.setup.state;

import com.github.derrop.documents.Document;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.configuration.DefaultAutumnConfiguration;
import dev.dementisimus.autumn.common.helper.NumberHelper;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultSetupState @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:07
 */
public class DefaultSetupState implements SetupState {

    private final String name;
    private final String messageTranslationProperty;

    private Object value;

    public DefaultSetupState(String name, String messageTranslationProperty, Object value) {
        this.name = name;
        this.messageTranslationProperty = messageTranslationProperty;
        this.value = value;
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public <T> T value(Class<T> clazz) {
        return (T) this.value();
    }

    @Override
    public void value(Object value) {
        this.value = value;
    }

    @Override
    public String messageTranslationProperty() {
        return this.messageTranslationProperty;
    }

    @Override
    public String asString() {
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
    public File asFile() {
        return new File(this.asString());
    }

    @Override
    public boolean isPresentInConfigFile(File configFile) {
        AutumnConfiguration configuration = new DefaultAutumnConfiguration(configFile);
        Document document = configuration.read();

        return document != null && document.keys() != null && document.contains(this.name);
    }

    @Override
    public boolean isExtraState(SetupManager setupManager) {
        return setupManager.isExtraState(this);
    }

    @Override
    public String name() {
        return this.name;
    }
}