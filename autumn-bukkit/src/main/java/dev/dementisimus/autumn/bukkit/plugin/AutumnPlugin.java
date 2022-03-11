/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AutumnPlugin extends JavaPlugin {

    public static <T> T of(String pluginName, Class<T> clazz) {
        return (T) Bukkit.getPluginManager().getPlugin(pluginName);
    }
}
