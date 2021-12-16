/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.listener;

import com.google.inject.Inject;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import javax.annotation.Nullable;

@AutumnListener
public class PlayerLocaleChangeListener implements Listener {

    @Inject @Nullable Storage storage;

    @EventHandler
    public void on(PlayerLocaleChangeEvent event) {
        PlayerLanguage.set(this.storage, event.getPlayer().getUniqueId(), event.getLocale());
    }
}
