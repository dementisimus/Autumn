package dev.dementisimus.autumn.common.api.setup.event;

import dev.dementisimus.autumn.common.api.setup.state.SetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class ValidateCurrentExtraSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 03.12.2021:19:11
 */
public interface ValidateCurrentExtraSetupStateEvent {

    SetupState currentSetupState();

    String consoleInput();

    boolean validInput();

    void validInput(boolean validInput);
}
