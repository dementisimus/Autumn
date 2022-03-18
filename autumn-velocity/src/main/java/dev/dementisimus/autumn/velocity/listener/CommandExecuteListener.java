/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.setup.CustomSetupManager;
import dev.dementisimus.autumn.common.setup.value.SetupValueManager;

public class CommandExecuteListener extends SetupValueManager {

    public CommandExecuteListener(CustomAutumn autumn, CustomSetupManager setupManager) {
        super(autumn, setupManager);
    }

    @Subscribe
    public void on(CommandExecuteEvent event) {
        String command = event.getCommand();

        if(this.setupManager.isCompleted() || command.equalsIgnoreCase("stop")) {
            event.setResult(CommandExecuteEvent.CommandResult.allowed());
            return;
        }

        event.setResult(CommandExecuteEvent.CommandResult.denied());
        this.setConsoleInput(command);
    }
}
