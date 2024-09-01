/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.i18n;

import dev.dementisimus.autumn.api.i18n.PlayerTranslation;
import dev.dementisimus.autumn.plugin.language.PlayerLanguage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomPlayerTranslation extends CustomTranslation implements PlayerTranslation {

    public CustomPlayerTranslation(String translationProperty) {
        super(translationProperty);
        super.parseConsoleColorCodes(false);
    }

    public @NotNull String get(Player player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }

    @Override
    public void send(Player player) {
        player.sendMessage(this.get(player));
    }
}
