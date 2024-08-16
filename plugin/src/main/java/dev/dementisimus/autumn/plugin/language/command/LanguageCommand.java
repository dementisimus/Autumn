/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.language.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.command.CustomAutumnCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class LanguageCommand extends CustomAutumnCommand {

    private final CustomAutumn autumn;

    public LanguageCommand(CustomAutumn autumn) {
        super("language", List.of("lang", "autumnlang", "alang"));

        this.autumn = autumn;
    }

    @Override
    public void register(LiteralArgumentBuilder<Player> builder) {
        builder.executes(commandContext -> {
            this.autumn.getLanguageSelection().open(commandContext.getSource());
            return 1;
        });
    }
}
