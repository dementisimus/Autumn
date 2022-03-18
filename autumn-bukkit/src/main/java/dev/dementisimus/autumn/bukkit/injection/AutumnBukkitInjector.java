/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.injection;

import dev.dementisimus.autumn.bukkit.BukkitAutumn;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnCommand;
import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.injection.CustomAutumnInjector;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutumnBukkitInjector extends CustomAutumnInjector {

    private final BukkitAutumn autumn;
    private final AutumnLogging logging;
    private final Plugin plugin;

    public AutumnBukkitInjector(BukkitAutumn autumn, Plugin plugin) {
        this.autumn = autumn;
        this.logging = this.autumn.logging();
        this.plugin = plugin;
    }

    @Override
    public void registerListener(Class<?> clazz, Object listenerObject) {
        if(listenerObject instanceof Listener listener) {
            Bukkit.getPluginManager().registerEvents(listener, this.plugin);
        }else {
            AutumnTranslation translation = new CustomAutumnTranslation("autumn.injection.class.not.a.listener");
            translation.replacement("class", listenerObject.getClass().getCanonicalName());

            this.logging.error(translation);
        }
    }

    @Override
    public void registerCommand(Class<?> clazz, Object commandObject) {
        AutumnCommand autumnCommand = clazz.getAnnotation(AutumnCommand.class);

        if(commandObject instanceof CommandExecutor commandExecutor) {
            List<String> commandNames = new ArrayList<>(Arrays.asList(autumnCommand.nameAliases()));
            commandNames.add(autumnCommand.name());

            for(String commandName : commandNames) {
                PluginCommand pluginCommand = Bukkit.getPluginCommand(commandName);

                if(pluginCommand != null) {
                    pluginCommand.setExecutor(commandExecutor);
                }else {
                    AutumnTranslation translation = new CustomAutumnTranslation("autumn.injection.command.not.in.plugin.yml");
                    translation.replacement("commandObject", commandName);

                    this.logging.error(translation);
                }
            }
        }else {
            AutumnTranslation translation = new CustomAutumnTranslation("autumn.injection.class.not.a.command");
            translation.replacement("class", commandObject.getClass().getCanonicalName());

            this.logging.error(translation);
        }
    }
}
