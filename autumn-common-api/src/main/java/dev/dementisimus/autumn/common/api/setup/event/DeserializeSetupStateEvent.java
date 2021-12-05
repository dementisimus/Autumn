package dev.dementisimus.autumn.common.api.setup.event;

import com.github.derrop.documents.Document;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DeserializeSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 03.12.2021:19:10
 */
public interface DeserializeSetupStateEvent {

    SetupState setupState();

    Document configuration();

    String name();

    Object value();

    void value(Object value);
}
