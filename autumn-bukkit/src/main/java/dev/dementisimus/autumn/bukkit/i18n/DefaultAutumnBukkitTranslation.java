package dev.dementisimus.autumn.bukkit.i18n;

import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.common.i18n.DefaultAutumnTranslation;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Locale;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultAutumnBukkitTranslation @ BukkitAutumn
 *
 * @author dementisimus
 * @since 26.11.2021:16:28
 */
public class DefaultAutumnBukkitTranslation extends DefaultAutumnTranslation implements AutumnBukkitTranslation {

    public DefaultAutumnBukkitTranslation(String translationProperty) {
        super(translationProperty);
    }

    public String get(Player player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }
}
