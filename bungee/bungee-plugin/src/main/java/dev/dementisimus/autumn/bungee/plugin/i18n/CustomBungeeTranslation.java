/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bungee.plugin.i18n;

import dev.dementisimus.autumn.bungee.api.i18n.AutumnBungeeTranslation;
import dev.dementisimus.autumn.common.plugin.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.plugin.language.PlayerLanguage;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

public class CustomBungeeTranslation extends CustomAutumnTranslation implements AutumnBungeeTranslation {

    public CustomBungeeTranslation(String translationProperty) {
        super(translationProperty);
        super.parseConsoleColorCodes(false);
    }

    @Override
    public @NotNull String get(@NotNull ProxiedPlayer player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }

    @Override
    public void send(@NotNull ProxiedPlayer player) {
        player.sendMessage(TextComponent.fromLegacyText(this.get(player)));
    }
}
