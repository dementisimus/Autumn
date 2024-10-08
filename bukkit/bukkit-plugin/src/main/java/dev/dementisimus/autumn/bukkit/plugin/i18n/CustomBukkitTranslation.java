/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.i18n;

import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.common.plugin.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.plugin.language.PlayerLanguage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomBukkitTranslation extends CustomAutumnTranslation implements AutumnBukkitTranslation {

    public CustomBukkitTranslation(String translationProperty) {
        super(translationProperty);
        super.parseConsoleColorCodes(false);
    }

    public @NotNull String get(@NotNull Player player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }

    @Override
    public void send(@NotNull Player player) {
        player.sendMessage(this.get(player));
    }
}
