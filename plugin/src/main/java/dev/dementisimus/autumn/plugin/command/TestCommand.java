/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import org.bukkit.entity.Player;

import java.util.List;

public class TestCommand extends CustomAutumnCommand {

    private final CustomAutumn autumn;

    public TestCommand(CustomAutumn autumn) {
        super("test", "test", List.of("test1", "test2", "test3"));

        this.autumn = autumn;
    }

    @Override
    public void register(LiteralArgumentBuilder<Player> builder) {
        builder.executes(commandContext -> {
            commandContext.getSource().sendRichMessage("<rainbow>heyyy: " + this.autumn);
            return 1;
        });
    }
}
