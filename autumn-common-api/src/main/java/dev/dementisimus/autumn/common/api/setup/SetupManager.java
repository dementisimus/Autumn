package dev.dementisimus.autumn.common.api.setup;

import dev.dementisimus.autumn.common.api.setup.state.SetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SetupManager @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:01
 */
public interface SetupManager {

    void mainSetupState(SetupState setupState);

    void extraSetupState(SetupState setupState);

    SetupState currentSetupState();

    void currentSetupState(SetupState setupState);

    void printSetupStateInstructions(SetupState setupState);

    void updateCurrentSetupState(Object value);

    void begin();

    void complete(boolean postSetup);

    boolean isCompleted();

    boolean isExtraState(SetupState setupState);
}
