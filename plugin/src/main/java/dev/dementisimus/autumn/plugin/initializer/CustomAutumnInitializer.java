/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.initializer;

import dev.dementisimus.autumn.api.Autumn;
import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.initializer.AutumnInitializer;
import dev.dementisimus.autumn.api.setup.state.SetupState;
import dev.dementisimus.autumn.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomAutumnInitializer implements AutumnInitializer {

    private final List<SetupState> extraSetupStates = new ArrayList<>();
    private final List<StorageSourceProperty> storageSourceProperties = new ArrayList<>();

    private final Plugin plugin;
    private String pluginPrefix;

    private boolean defaultSetupStates;
    private boolean storageSetupStates;

    @ApiStatus.Internal
    public CustomAutumnInitializer(Plugin plugin) {
        this.plugin = plugin;
    }

    public static AutumnInitializer of(Plugin plugin) {
        return new CustomAutumnInitializer(plugin);
    }

    @Override
    public AutumnInitializer setPluginPrefix(String prefix) {
        this.pluginPrefix = prefix;
        return this;
    }

    @Override
    public AutumnInitializer enableDefaultSetupStates() {
        this.defaultSetupStates = true;
        return this;
    }

    @Override
    public AutumnInitializer enableStorageSetupStates() {
        this.storageSetupStates = true;
        return this;
    }

    @Override
    public AutumnInitializer enableExtraSetupStates(SetupState... setupStates) {
        for (SetupState setupState : setupStates) {
            this.enableExtraSetupState(setupState);
        }
        return this;
    }

    @Override
    public AutumnInitializer enableExtraSetupState(SetupState setupState) {
        this.extraSetupStates.add(setupState);
        return this;
    }

    @Override
    public AutumnInitializer enableStorage(StorageSourceProperty... storageSourceProperties) {
        this.storageSourceProperties.addAll(List.of(storageSourceProperties));
        return this;
    }

    @Override
    public void initialize(SingleCallback<Autumn> initializationCallback) {
        String pluginPrefix = this.pluginPrefix == null ? "§6§l" + this.plugin.getName() + " §7§l»" : this.pluginPrefix;
        new CustomAutumn(this.plugin, pluginPrefix).initialize(this, initializationCallback);
    }
}
