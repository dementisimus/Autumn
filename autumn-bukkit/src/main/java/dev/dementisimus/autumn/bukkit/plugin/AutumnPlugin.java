package dev.dementisimus.autumn.bukkit.plugin;

import dev.dementisimus.autumn.bukkit.BukkitAutumn;
import dev.dementisimus.autumn.common.api.Autumn;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnPlugin @ BukkitAutumn
 *
 * @author dementisimus
 * @since 23.11.2021:14:04
 */
public class AutumnPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Autumn autumn = new BukkitAutumn(this);
        autumn.databaseSetupStates();
        autumn.enableDatabase();

        autumn.initialize(autumnInjector -> {

        });
    }
}
