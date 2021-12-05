package dev.dementisimus.autumn.common.api.setup.state;

import dev.dementisimus.autumn.common.api.setup.SetupManager;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupState @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:02
 */
public interface SetupState {

    Object value();

    <T> T value(Class<T> clazz);

    void value(Object value);

    String messageTranslationProperty();

    String asString();

    int asInteger();

    boolean asBoolean();

    File asFile();

    boolean isPresentInConfigFile(File configFile);

    boolean isExtraState(SetupManager setupManager);

    String name();
}
