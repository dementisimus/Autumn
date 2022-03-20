/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.language.command;

import com.google.inject.Inject;
import dev.dementisimus.autumn.bukkit.BukkitAutumn;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AutumnCommand(name = "language", nameAliases = {"lang"})
public class LanguageCommand implements CommandExecutor {

    @Inject BukkitAutumn autumn;

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String l, @NotNull String[] args) {
        if(cs instanceof Player player) {
            this.autumn.getLanguageSelection().open(player);
        }
        return false;
    }
}
