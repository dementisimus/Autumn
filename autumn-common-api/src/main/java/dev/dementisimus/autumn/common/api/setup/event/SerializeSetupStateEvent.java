package dev.dementisimus.autumn.common.api.setup.event;

import dev.dementisimus.autumn.common.api.setup.state.SetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SerializeSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 03.12.2021:19:10
 */
public interface SerializeSetupStateEvent {

    SetupState setupState();

    Object value();

    void value(Object value);
}
