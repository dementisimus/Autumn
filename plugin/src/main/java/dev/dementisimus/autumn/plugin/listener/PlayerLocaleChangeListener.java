/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.listener;

import dev.dementisimus.autumn.api.storage.Storage;
import dev.dementisimus.autumn.plugin.language.PlayerLanguage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

public class PlayerLocaleChangeListener implements Listener {

    private final Storage storage;

    public PlayerLocaleChangeListener(Storage storage) {
        this.storage = storage;
    }

    @EventHandler
    public void on(PlayerLocaleChangeEvent event) {
        PlayerLanguage.set(this.storage, event.getPlayer().getUniqueId(), event.getLocale());
    }
}
