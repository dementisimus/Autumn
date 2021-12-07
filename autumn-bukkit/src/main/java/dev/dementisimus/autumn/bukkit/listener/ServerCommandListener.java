/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.listener;

import dev.dementisimus.autumn.common.DefaultAutumn;
import dev.dementisimus.autumn.common.setup.DefaultSetupManager;
import dev.dementisimus.autumn.common.setup.value.SetupValueManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class ServerCommandListener extends SetupValueManager implements Listener {

    public ServerCommandListener(DefaultAutumn autumn, DefaultSetupManager setupManager) {
        super(autumn, setupManager);
    }

    @EventHandler
    public void on(ServerCommandEvent event) {
        String command = event.getCommand();

        if(this.setupManager.isCompleted() || command.equalsIgnoreCase("stop")) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);
        this.setConsoleInput(command);
    }
}
