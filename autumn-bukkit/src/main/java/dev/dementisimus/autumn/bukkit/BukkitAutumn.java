package dev.dementisimus.autumn.bukkit;

import dev.dementisimus.autumn.bukkit.api.language.LanguageSelection;
import dev.dementisimus.autumn.bukkit.executor.AutumnBukkitTaskExecutor;
import dev.dementisimus.autumn.bukkit.injection.AutumnBukkitInjector;
import dev.dementisimus.autumn.bukkit.language.DefaultLanguageSelection;
import dev.dementisimus.autumn.bukkit.listener.ServerCommandListener;
import dev.dementisimus.autumn.bukkit.log.AutumnBukkitLogging;
import dev.dementisimus.autumn.bukkit.setup.BukkitSetupManager;
import dev.dementisimus.autumn.common.DefaultAutumn;
import dev.dementisimus.autumn.common.api.server.ServerType;
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.injection.DefaultAutumnInjector;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultAutumn @ BukkitAutumn
 *
 * @author dementisimus
 * @since 22.11.2021:20:56
 */
public class BukkitAutumn extends DefaultAutumn {

    @Getter private LanguageSelection languageSelection;

    public BukkitAutumn(Plugin plugin) {
        super(plugin, new AutumnBukkitTaskExecutor(plugin), new AutumnBukkitLogging());

        this.setAutumnClassLoader(this.getClass().getClassLoader());

        SetupManager setupManager = this.getSetupManager();
        if(!setupManager.isCompleted()) {
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
        DefaultAutumnInjector injector = this.getInjector();

        injector.registerModule(BukkitAutumn.class, this);
        injector.registerModule(Plugin.class, plugin);

        this.languageSelection = new DefaultLanguageSelection(this);
    }

    @SneakyThrows
    @Override
    protected void loadPlugin(File pluginFile) {
        Plugin plugin = Bukkit.getPluginManager().loadPlugin(pluginFile);

        if(plugin != null) {
            Bukkit.getPluginManager().enablePlugin(plugin);
        }
    }

    @Override
    protected boolean isLoadedPlugin(String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    @Override
    protected DefaultAutumnInjector getAutumnInjector(Object pluginObject) {
        return new AutumnBukkitInjector(this, (Plugin) pluginObject);
    }

    private ServerType checkForPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return ServerType.PAPER;
        }catch(ClassNotFoundException ignored) {
            return ServerType.SPIGOT;
        }
    }
}
