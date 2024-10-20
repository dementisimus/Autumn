/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.command;

import com.google.common.base.Joiner;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DummyCommand extends Command implements PluginIdentifiableCommand {

    private final Plugin plugin;
    private final CustomAutumnCommand command;

    public DummyCommand(Plugin plugin, CustomAutumnCommand command) {
        super(command.getLabel());

        this.plugin = plugin;
        this.command = command;

        super.setAliases(command.getAliases());
        super.setUsage("/" + command.getLabel());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!super.testPermissionSilent(sender)) return true;

        if (sender instanceof Player player) {
            Commands dispatcher = ((CraftServer) Bukkit.getServer()).getServer().resources.managers().commands;
            CommandSourceStack source = ((CraftPlayer) player).getHandle().createCommandSourceStack();
            dispatcher.performPrefixedCommand(source, this.toDispatcher(super.getLabel(), List.of(args)), this.toDispatcher(commandLabel, this.command.getAliases()));
        }

        return true;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.plugin;
    }

    private String toDispatcher(String label, List<String> args) {
        return label + (!args.isEmpty() ? " " + Joiner.on(' ').join(args) : "");
    }
}
