/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.input;

import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.bukkit.api.input.UserTextInput;
import dev.dementisimus.autumn.bukkit.i18n.CustomBukkitTranslation;
import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CustomUserTextInput implements UserTextInput {

    public static final Map<Player, AutumnSingleCallback<String>> TEXT_INPUT_REQUESTS = new HashMap<>();

    private String prefix;
    private String translationProperty;
    private Player player;

    @Override
    public void prefix(@NotNull String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void translationProperty(@NotNull String translationProperty) {
        this.translationProperty = translationProperty;
    }

    @Override
    public void player(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public void fetch(@NotNull AutumnSingleCallback<@NotNull String> stringCallback) {
        this.player.closeInventory();

        AutumnBukkitTranslation autumnTranslation = new CustomBukkitTranslation(this.translationProperty);

        autumnTranslation.replacement("prefix", this.prefix);
        autumnTranslation.send(this.player);

        this.player.playSound(this.player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 10, 1);

        TEXT_INPUT_REQUESTS.put(this.player, stringCallback);
    }
}
