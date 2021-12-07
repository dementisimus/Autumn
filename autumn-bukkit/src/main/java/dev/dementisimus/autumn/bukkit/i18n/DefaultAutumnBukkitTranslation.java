/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.i18n;

import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.common.i18n.DefaultAutumnTranslation;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DefaultAutumnBukkitTranslation extends DefaultAutumnTranslation implements AutumnBukkitTranslation {

    public DefaultAutumnBukkitTranslation(String translationProperty) {
        super(translationProperty);
    }

    public @NotNull String get(@NotNull Player player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }
}
