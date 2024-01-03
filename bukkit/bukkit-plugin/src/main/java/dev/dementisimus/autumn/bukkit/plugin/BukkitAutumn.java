/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin;

import dev.dementisimus.autumn.bukkit.api.language.LanguageSelection;
import dev.dementisimus.autumn.bukkit.plugin.executor.AutumnBukkitTaskExecutor;
import dev.dementisimus.autumn.bukkit.plugin.injection.AutumnBukkitInjector;
import dev.dementisimus.autumn.bukkit.plugin.language.CustomLanguageSelection;
import dev.dementisimus.autumn.bukkit.plugin.listener.ServerCommandListener;
import dev.dementisimus.autumn.bukkit.plugin.log.AutumnBukkitLogging;
import dev.dementisimus.autumn.bukkit.plugin.setup.BukkitSetupManager;
import dev.dementisimus.autumn.common.api.server.ServerType;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.plugin.CustomAutumn;
import dev.dementisimus.autumn.common.plugin.injection.CustomAutumnInjector;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class BukkitAutumn extends CustomAutumn {

    @Getter
    private LanguageSelection languageSelection;

    public BukkitAutumn(Plugin plugin, String pluginPrefix) {
        super(plugin, pluginPrefix, new AutumnBukkitTaskExecutor(plugin), new AutumnBukkitLogging());

        this.setAutumnClassLoader(this.getClass().getClassLoader());

        SetupManager setupManager = this.getSetupManager();
        if (!setupManager.isCompleted()) {
            ServerCommandListener serverCommandListener = new ServerCommandListener(this, this.getSetupManager());

            this.setSetupValueManager(serverCommandListener);

            Bukkit.getPluginManager().registerEvents(serverCommandListener, plugin);
        }
    }

    @Override
    protected void initializePluginDetails(Object pluginObject) {
        Plugin plugin = (Plugin) pluginObject;

        super.setSetupManager(new BukkitSetupManager(this));
        super.setPluginClassLoader(plugin.getClass().getClassLoader());
        super.setPluginName(plugin.getName());
        super.setPluginVersion(plugin.getDescription().getVersion());
        super.setServertype(this.checkForPaper());
        super.setServerVersion(Bukkit.getVersion());
        super.setPluginFolder(plugin.getDataFolder());
    }

    @Override
    protected void initializePlugin(Object pluginObject) {
        Plugin plugin = (Plugin) pluginObject;
        CustomAutumnInjector injector = this.getInjector();

        injector.registerModule(BukkitAutumn.class, this);
        injector.registerModule(Plugin.class, plugin);

        this.languageSelection = new CustomLanguageSelection(this);
    }

    @SneakyThrows
    @Override
    protected void loadPlugin(File pluginFile) {
        Plugin plugin = Bukkit.getPluginManager().loadPlugin(pluginFile);

        if (plugin != null) {
            Bukkit.getPluginManager().enablePlugin(plugin);
        }
    }

    @Override
    protected boolean isLoadedPlugin(String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    @Override
    protected CustomAutumnInjector getAutumnInjector(Object pluginObject) {
        return new AutumnBukkitInjector(this, (Plugin) pluginObject);
    }

    private ServerType checkForPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return ServerType.PAPER;
        } catch (ClassNotFoundException ignored) {
            return ServerType.SPIGOT;
        }
    }
}
