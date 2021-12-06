package dev.dementisimus.autumn.bukkit.listener;

import com.google.inject.Inject;
import dev.dementisimus.autumn.common.api.database.Database;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import javax.annotation.Nullable;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class PlayerLocaleChangeListener @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:22:50
 */
@AutumnListener
public class PlayerLocaleChangeListener implements Listener {

    @Inject @Nullable Database database;

    @EventHandler
    public void on(PlayerLocaleChangeEvent event) {
        PlayerLanguage.set(this.database, event.getPlayer().getUniqueId(), event.getLocale());
    }
}
