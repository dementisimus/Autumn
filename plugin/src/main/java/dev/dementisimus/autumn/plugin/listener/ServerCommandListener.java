/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.listener;

import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.setup.CustomSetupManager;
import dev.dementisimus.autumn.plugin.setup.value.SetupValueManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class ServerCommandListener extends SetupValueManager implements Listener {

    public ServerCommandListener(CustomAutumn autumn, CustomSetupManager setupManager) {
        super(autumn, setupManager);
    }

    @EventHandler
    public void on(ServerCommandEvent event) {
        String command = event.getCommand();

        if (this.setupManager.isCompleted() || command.equalsIgnoreCase("stop")) {
            event.setCancelled(false);
            return;
        }

        event.setCancelled(true);
        this.setConsoleInput(command);
    }
}
