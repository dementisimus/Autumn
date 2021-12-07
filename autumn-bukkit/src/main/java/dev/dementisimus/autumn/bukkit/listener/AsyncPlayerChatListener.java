/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.listener;

import dev.dementisimus.autumn.bukkit.input.CustomUserTextInput;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
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
        AutumnCallback<String> stringCallback = CustomUserTextInput.TEXT_INPUT_REQUESTS.get(player);

        if(stringCallback != null) {
            event.setCancelled(true);

            stringCallback.done(message);
            CustomUserTextInput.TEXT_INPUT_REQUESTS.remove(player);
        }
    }
}
