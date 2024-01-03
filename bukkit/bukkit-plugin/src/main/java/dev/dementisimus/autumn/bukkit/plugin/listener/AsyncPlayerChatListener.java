/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.listener;

import dev.dementisimus.autumn.bukkit.plugin.input.CustomUserTextInput;
import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@AutumnListener
public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        AutumnSingleCallback<String> stringCallback = CustomUserTextInput.TEXT_INPUT_REQUESTS.get(player);

        if (stringCallback != null) {
            event.setCancelled(true);

            stringCallback.done(message);
            CustomUserTextInput.TEXT_INPUT_REQUESTS.remove(player);
        }
    }
}
