/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.injection;

import com.google.inject.Injector;
import dev.dementisimus.autumn.bukkit.BukkitAutumn;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnCommand;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnSetupListener;
import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.injection.CustomAutumnInjector;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.annotation.Annotation;
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
    public void register(Class<? extends Annotation> annotation, Class<?> clazz, Injector injector) {
        if(annotation.equals(AutumnListener.class)) {
            this.registerOptionalListener(clazz, injector.getInstance(clazz));
        }else if(annotation.equals(AutumnSetupListener.class)) {
            this.registerListener(injector.getInstance(clazz));
        }else if(annotation.equals(AutumnCommand.class)) {
            this.registerCommand(clazz, injector.getInstance(clazz));
        }
    }

    private void registerListener(Object listenerObject) {
        if(listenerObject instanceof Listener listener) {
            Bukkit.getPluginManager().registerEvents(listener, this.plugin);
        }else {
            AutumnTranslation translation = new CustomAutumnTranslation("autumn.injection.class.not.a.listener");
            translation.replacement("class", listenerObject.getClass().getCanonicalName());

            this.logging.error(translation);
        }
    }

    private void registerOptionalListener(Class<?> clazz, Object listener) {
        boolean isOptional = clazz.getAnnotation(AutumnListener.class).isOptional();

        if(!isOptional || this.autumn.optionalListeners()) {
            this.registerListener(listener);
        }
    }

    private void registerCommand(Class<?> clazz, Object commandObject) {
        AutumnCommand autumnCommand = clazz.getAnnotation(AutumnCommand.class);

        if(commandObject instanceof CommandExecutor commandExecutor) {
            if(!autumnCommand.isOptional() || this.autumn.optionalCommands()) {

                List<String> commandNames = new ArrayList<>(Arrays.asList(autumnCommand.nameAliases()));
                commandNames.add(autumnCommand.name());

                for(String commandName : commandNames) {
                    PluginCommand pluginCommand = Bukkit.getPluginCommand(commandName);

                    if(pluginCommand != null) {
                        pluginCommand.setExecutor(commandExecutor);
                    }else {
                        AutumnTranslation translation = new CustomAutumnTranslation("autumn.injection.command.not.in.plugin.yml");
                        translation.replacement("command", commandName);

                        this.logging.error(translation);
                    }
                }
            }
        }else {
            AutumnTranslation translation = new CustomAutumnTranslation("autumn.injection.class.not.a.command");
            translation.replacement("class", commandObject.getClass().getCanonicalName());

            this.logging.error(translation);
        }
    }
}
