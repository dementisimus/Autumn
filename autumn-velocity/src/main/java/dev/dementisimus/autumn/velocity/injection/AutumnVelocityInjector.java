/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.injection;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.EventManager;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnCommand;
import dev.dementisimus.autumn.common.debug.SysOut;
import dev.dementisimus.autumn.common.injection.CustomAutumnInjector;
import dev.dementisimus.autumn.velocity.VelocityAutumn;

public class AutumnVelocityInjector extends CustomAutumnInjector {

    private final Object plugin;
    private final EventManager eventManager;
    private final CommandManager commandManager;

    public AutumnVelocityInjector(VelocityAutumn velocityAutumn, EventManager eventManager, CommandManager commandManager) {
        this.plugin = velocityAutumn.plugin();
        this.eventManager = eventManager;
        this.commandManager = commandManager;
    }

    @Override
    public void registerListener(Class<?> clazz, Object listenerObject) {
        this.eventManager.register(this.plugin, listenerObject);
    }

    @Override
    public void registerCommand(Class<?> clazz, Object commandObject) {
        AutumnCommand autumnCommand = clazz.getAnnotation(AutumnCommand.class);

        if(commandObject instanceof Command command) {
            CommandMeta commandMeta = this.commandManager.metaBuilder(autumnCommand.name()).aliases(autumnCommand.nameAliases()).build();

            this.commandManager.register(commandMeta, command);
        }else {
            SysOut.debug("NO_COMMAND", clazz.getCanonicalName());
        }
    }
}
