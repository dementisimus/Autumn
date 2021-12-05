package dev.dementisimus.autumn.bukkit.language.command;

import com.google.inject.Inject;
import dev.dementisimus.autumn.bukkit.BukkitAutumn;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class LanguageCommand @ Autumn
 *
 * @author dementisimus
 * @since 05.12.2021:14:01
 */
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
