/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.setup;

import com.velocitypowered.api.event.EventManager;
import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.setup.event.DeserializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.NextExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.SerializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.ValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.setup.CustomSetupManager;
import dev.dementisimus.autumn.velocity.setup.event.VelocityDeserializeSetupStateEvent;
import dev.dementisimus.autumn.velocity.setup.event.VelocityNextExtraSetupStateEvent;
import dev.dementisimus.autumn.velocity.setup.event.VelocitySerializeSetupStateEvent;
import dev.dementisimus.autumn.velocity.setup.event.VelocityValidateCurrentExtraSetupStateEvent;
import lombok.SneakyThrows;
import org.bson.Document;

public class VelocitySetupManager extends CustomSetupManager {

    private final EventManager eventManager;

    public VelocitySetupManager(CustomAutumn autumn, EventManager eventManager) {
        super(autumn);

        this.eventManager = eventManager;
    }

    @SneakyThrows
    @Override
    public ValidateCurrentExtraSetupStateEvent callValidateCurrentSetupStateEvent(SetupState currentSetupState, String consoleInput, boolean validInput) {
        return this.eventManager.fire(new VelocityValidateCurrentExtraSetupStateEvent(currentSetupState, consoleInput, validInput)).get();
    }

    @SneakyThrows
    @Override
    protected DeserializeSetupStateEvent callDeserializeSetupStateEvent(SetupState setupState, Document configuration, String name, Object value) {
        return this.eventManager.fire(new VelocityDeserializeSetupStateEvent(setupState, configuration, name, value)).get();
    }

    @SneakyThrows
    @Override
    protected NextExtraSetupStateEvent callNextSetupStateEvent(SetupState currentSetupState, int currentStateListIndex, SetupState nextSetupState, boolean cancelled) {
        return this.eventManager.fire(new VelocityNextExtraSetupStateEvent(this, currentSetupState, currentStateListIndex, nextSetupState, cancelled)).get();
    }

    @SneakyThrows
    @Override
    protected SerializeSetupStateEvent callSerializeSetupStateEvent(SetupState setupState, Object value) {
        return this.eventManager.fire(new VelocitySerializeSetupStateEvent(setupState, value)).get();
    }
}
