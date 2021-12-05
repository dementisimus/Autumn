package dev.dementisimus.autumn.bukkit.api.input;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import org.bukkit.entity.Player;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class UserTextInput @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:14:33
 */
public interface UserTextInput {

    void prefix(String prefix);

    void translationProperty(String translationProperty);

    void player(Player player);

    void fetch(AutumnCallback<String> stringCallback);
}
