package dev.dementisimus.autumn.bungee.i18n;

import dev.dementisimus.autumn.bungee.api.i18n.AutumnBungeeTranslation;
import dev.dementisimus.autumn.common.i18n.DefaultAutumnTranslation;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import net.md_5.bungee.api.connection.ProxiedPlayer;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultAutumnBungeeTranslation @ Autumn
 *
 * @author dementisimus
 * @since 05.12.2021:21:48
 */
public class DefaultAutumnBungeeTranslation extends DefaultAutumnTranslation implements AutumnBungeeTranslation {

    public DefaultAutumnBungeeTranslation(String translationProperty) {
        super(translationProperty);
    }

    @Override
    public String get(ProxiedPlayer player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }
}
