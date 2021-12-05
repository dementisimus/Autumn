package dev.dementisimus.autumn.bukkit.listener;

import dev.dementisimus.autumn.bukkit.input.DefaultUserTextInput;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AsyncPlayerChatListener @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:14:41
 */
@AutumnListener
public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        AutumnCallback<String> stringCallback = DefaultUserTextInput.TEXT_INPUT_REQUESTS.get(player);

        if(stringCallback != null) {
            event.setCancelled(true);

            stringCallback.done(message);
            DefaultUserTextInput.TEXT_INPUT_REQUESTS.remove(player);
        }
    }
}
