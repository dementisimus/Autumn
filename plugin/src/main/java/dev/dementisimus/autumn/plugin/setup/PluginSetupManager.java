/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.setup;

import dev.dementisimus.autumn.api.setup.state.SetupState;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.setup.event.DeserializeSetupStateEvent;
import dev.dementisimus.autumn.plugin.setup.event.NextExtraSetupStateEvent;
import dev.dementisimus.autumn.plugin.setup.event.SerializeSetupStateEvent;
import dev.dementisimus.autumn.plugin.setup.event.ValidateCurrentExtraSetupStateEvent;
import org.bson.Document;
import org.bukkit.Bukkit;

public class PluginSetupManager extends CustomSetupManager {

    public PluginSetupManager(CustomAutumn autumn) {
        super(autumn);
    }

    @Override
    public dev.dementisimus.autumn.api.setup.event.ValidateCurrentExtraSetupStateEvent callValidateCurrentSetupStateEvent(SetupState currentSetupState, String consoleInput, boolean validInput) {
        ValidateCurrentExtraSetupStateEvent validateCurrentExtraSetupStateEvent = new ValidateCurrentExtraSetupStateEvent(currentSetupState, consoleInput, validInput);

        Bukkit.getPluginManager().callEvent(validateCurrentExtraSetupStateEvent);
        return validateCurrentExtraSetupStateEvent;
    }

    @Override
    protected dev.dementisimus.autumn.api.setup.event.DeserializeSetupStateEvent callDeserializeSetupStateEvent(SetupState setupState, Document configuration, String name, Object value) {
        DeserializeSetupStateEvent deserializeSetupStateEvent = new DeserializeSetupStateEvent(setupState, configuration, name, value);

        Bukkit.getPluginManager().callEvent(deserializeSetupStateEvent);
        return deserializeSetupStateEvent;
    }

    @Override
    protected dev.dementisimus.autumn.api.setup.event.NextExtraSetupStateEvent callNextSetupStateEvent(SetupState currentSetupState, int currentStateListIndex, SetupState nextSetupState, boolean cancelled) {
        NextExtraSetupStateEvent nextExtraSetupStateEvent = new NextExtraSetupStateEvent(this, currentSetupState, currentStateListIndex, nextSetupState, cancelled);

        Bukkit.getPluginManager().callEvent(nextExtraSetupStateEvent);
        return nextExtraSetupStateEvent;
    }

    @Override
    protected dev.dementisimus.autumn.api.setup.event.SerializeSetupStateEvent callSerializeSetupStateEvent(SetupState setupState, Object value) {
        SerializeSetupStateEvent serializeSetupStateEvent = new SerializeSetupStateEvent(setupState, value);

        Bukkit.getPluginManager().callEvent(serializeSetupStateEvent);
        return serializeSetupStateEvent;
    }
}
