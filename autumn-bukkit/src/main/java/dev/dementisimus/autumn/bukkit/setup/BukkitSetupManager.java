/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.setup;

import dev.dementisimus.autumn.bukkit.setup.event.BukkitDeserializeSetupStateEvent;
import dev.dementisimus.autumn.bukkit.setup.event.BukkitNextExtraSetupStateEvent;
import dev.dementisimus.autumn.bukkit.setup.event.BukkitSerializeSetupStateEvent;
import dev.dementisimus.autumn.bukkit.setup.event.BukkitValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.setup.event.DeserializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.NextExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.SerializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.ValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.setup.CustomSetupManager;
import org.bson.Document;
import org.bukkit.Bukkit;

public class BukkitSetupManager extends CustomSetupManager {

    public BukkitSetupManager(CustomAutumn autumn) {
        super(autumn);
    }

    @Override
    public ValidateCurrentExtraSetupStateEvent callValidateCurrentSetupStateEvent(SetupState currentSetupState, String consoleInput, boolean validInput) {
        BukkitValidateCurrentExtraSetupStateEvent validateCurrentExtraSetupStateEvent = new BukkitValidateCurrentExtraSetupStateEvent(currentSetupState, consoleInput, validInput);

        Bukkit.getPluginManager().callEvent(validateCurrentExtraSetupStateEvent);
        return validateCurrentExtraSetupStateEvent;
    }

    @Override
    protected DeserializeSetupStateEvent callDeserializeSetupStateEvent(SetupState setupState, Document configuration, String name, Object value) {
        BukkitDeserializeSetupStateEvent deserializeSetupStateEvent = new BukkitDeserializeSetupStateEvent(setupState, configuration, name, value);

        Bukkit.getPluginManager().callEvent(deserializeSetupStateEvent);
        return deserializeSetupStateEvent;
    }

    @Override
    protected NextExtraSetupStateEvent callNextSetupStateEvent(SetupState currentSetupState, int currentStateListIndex, SetupState nextSetupState, boolean cancelled) {
        BukkitNextExtraSetupStateEvent nextExtraSetupStateEvent = new BukkitNextExtraSetupStateEvent(this, currentSetupState, currentStateListIndex, nextSetupState, cancelled);

        Bukkit.getPluginManager().callEvent(nextExtraSetupStateEvent);
        return nextExtraSetupStateEvent;
    }

    @Override
    protected SerializeSetupStateEvent callSerializeSetupStateEvent(SetupState setupState, Object value) {
        BukkitSerializeSetupStateEvent serializeSetupStateEvent = new BukkitSerializeSetupStateEvent(setupState, value);

        Bukkit.getPluginManager().callEvent(serializeSetupStateEvent);
        return serializeSetupStateEvent;
    }
}
