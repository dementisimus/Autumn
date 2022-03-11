/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.i18n;

import com.velocitypowered.api.proxy.Player;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import dev.dementisimus.autumn.velocity.api.i18n.AutumnVelocityTranslation;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class CustomVelocityTranslation extends CustomAutumnTranslation implements AutumnVelocityTranslation {

    public CustomVelocityTranslation(String translationProperty) {
        super(translationProperty);
        super.parseConsoleColorCodes(false);
    }

    @Override
    public @NotNull String get(@NotNull Player player) {
        return super.getMessage(PlayerLanguage.get(player.getUniqueId()));
    }

    @Override
    public void send(@NotNull Player player) {
        player.sendMessage(Component.text(this.get(player)));
    }
}
