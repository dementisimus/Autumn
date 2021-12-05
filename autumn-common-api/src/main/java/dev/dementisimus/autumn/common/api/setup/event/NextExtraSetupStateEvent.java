package dev.dementisimus.autumn.common.api.setup.event;

import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class NextExtraSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 03.12.2021:19:11
 */
public interface NextExtraSetupStateEvent {

    SetupManager setupManager();

    SetupState currentSetupState();

    int currentStateListIndex();

    SetupState nextSetupState();

    void nextSetupState(SetupState nextSetupState);

    boolean cancelled();

    void cancelled(boolean cancelled);
}
