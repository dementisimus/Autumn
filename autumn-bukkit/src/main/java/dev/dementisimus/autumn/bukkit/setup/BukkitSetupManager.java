package dev.dementisimus.autumn.bukkit.setup;

import com.github.derrop.documents.Document;
import dev.dementisimus.autumn.bukkit.setup.event.BukkitDeserializeSetupStateEvent;
import dev.dementisimus.autumn.bukkit.setup.event.BukkitNextExtraSetupStateEvent;
import dev.dementisimus.autumn.bukkit.setup.event.BukkitSerializeSetupStateEvent;
import dev.dementisimus.autumn.bukkit.setup.event.BukkitValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.DefaultAutumn;
import dev.dementisimus.autumn.common.api.setup.event.DeserializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.NextExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.SerializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.event.ValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.setup.DefaultSetupManager;
import org.bukkit.Bukkit;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class BukkitSetupManager @ Autumn
 *
 * @author dementisimus
 * @since 03.12.2021:19:37
 */
public class BukkitSetupManager extends DefaultSetupManager {

    public BukkitSetupManager(DefaultAutumn autumn) {
        super(autumn);
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

    @Override
    public ValidateCurrentExtraSetupStateEvent callValidateCurrentSetupStateEvent(SetupState currentSetupState, String consoleInput, boolean validInput) {
        BukkitValidateCurrentExtraSetupStateEvent validateCurrentExtraSetupStateEvent = new BukkitValidateCurrentExtraSetupStateEvent(currentSetupState, consoleInput, validInput);

        Bukkit.getPluginManager().callEvent(validateCurrentExtraSetupStateEvent);
        return validateCurrentExtraSetupStateEvent;
    }
}
