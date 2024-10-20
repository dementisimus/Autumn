/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.input;

import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.i18n.PlayerTranslation;
import dev.dementisimus.autumn.api.input.UserTextInput;
import dev.dementisimus.autumn.plugin.i18n.CustomPlayerTranslation;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

//ToDo implement generic user input
public class CustomUserTextInput implements UserTextInput {

    public static final Map<Player, SingleCallback<String>> TEXT_INPUT_REQUESTS = new HashMap<>();

    private String prefix;
    private String translationProperty;
    private Player player;

    @Override
    public void prefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void translationProperty(String translationProperty) {
        this.translationProperty = translationProperty;
    }

    @Override
    public void player(Player player) {
        this.player = player;
    }

    @Override
    public void fetch(SingleCallback<@NotNull String> stringCallback) {
        this.player.closeInventory();

        PlayerTranslation playerTranslation = new CustomPlayerTranslation(this.translationProperty);

        playerTranslation.argument("prefix", this.prefix);
        playerTranslation.send(this.player);

        this.player.playSound(this.player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 10, 1);

        TEXT_INPUT_REQUESTS.put(this.player, stringCallback);
    }
}
