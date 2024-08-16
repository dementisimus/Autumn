/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.listener;

import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.plugin.input.CustomUserTextInput;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(AsyncChatEvent event) {
        Player player = event.getPlayer();
        SingleCallback<String> stringCallback = CustomUserTextInput.TEXT_INPUT_REQUESTS.get(player);

        if (stringCallback != null) {
            event.setCancelled(true);

            stringCallback.done(LegacyComponentSerializer.legacySection().serialize(event.message()));
            CustomUserTextInput.TEXT_INPUT_REQUESTS.remove(player);
        }
    }
}
