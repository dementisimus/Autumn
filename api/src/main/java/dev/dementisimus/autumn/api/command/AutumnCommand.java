/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import org.bukkit.entity.Player;

/**
 * @since 2.0.0
 */
public interface AutumnCommand {

    /**
     * @since 2.0.0
     */
    void register(LiteralArgumentBuilder<Player> builder);

    /**
     * @since 2.0.0
     */
    default LiteralArgumentBuilder<Player> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * @since 2.0.0
     */
    default <T> RequiredArgumentBuilder<Player, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
