/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bungee.i18n;

import dev.dementisimus.autumn.bungee.api.i18n.AutumnBungeeTranslation;
import dev.dementisimus.autumn.common.i18n.DefaultAutumnTranslation;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DefaultAutumnBungeeTranslation extends DefaultAutumnTranslation implements AutumnBungeeTranslation {

    public DefaultAutumnBungeeTranslation(String translationProperty) {
        super(translationProperty);
    }

    @Override
    public String get(ProxiedPlayer player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }
}
