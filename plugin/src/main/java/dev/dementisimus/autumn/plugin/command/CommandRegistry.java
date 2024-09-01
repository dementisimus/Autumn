package dev.dementisimus.autumn.plugin.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.reflection.ReflectionField;
import dev.dementisimus.autumn.plugin.reflection.ReflectionMethod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.spigotmc.AsyncCatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry implements Listener {

    public static final SuggestionProvider<Player> SUMMONABLE_ENTITIES = (context, builder) -> SharedSuggestionProvider.suggestResource(BuiltInRegistries.ENTITY_TYPE.keySet(), builder);
    private static final ReflectionMethod<Server, SimpleCommandMap> COMMAND_MAP = ReflectionMethod.getterNonStatic(CraftServer.class, "commandMap", SimpleCommandMap.class);

    private final Plugin plugin;
    private final CustomAutumn autumn;

    public CommandRegistry(Plugin plugin, CustomAutumn autumn) {
        this.plugin = plugin;
        this.autumn = autumn;

        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    public void registerCommand(CustomAutumnCommand command) {
        AsyncCatcher.catchOp("command register");

        List<String> allLabels = new ArrayList<>(command.getAliases());
        allLabels.add(command.getLabel());

        if (COMMAND_MAP != null) {
            COMMAND_MAP.get(Bukkit.getServer()).register(this.plugin.getName(), new DummyCommand(this.plugin, command));

            ((CraftServer) Bukkit.getServer()).syncCommands();

            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                Commands commands = ((CraftServer) Bukkit.getServer()).getServer().resources.managers().commands;
                LiteralArgumentBuilder<Player> literalArgumentBuilder = LiteralArgumentBuilder.literal(command.getLabel());

                if (command.getPermission() != null && !command.getPermission().isEmpty() && !command.getPermission().isBlank()) {
                    literalArgumentBuilder.requires(player -> player.hasPermission(command.getPermission()));
                }

                command.register(literalArgumentBuilder);

                LiteralCommandNode<CommandSourceStack> commandNode = (LiteralCommandNode<CommandSourceStack>) this.transformNode(literalArgumentBuilder.build());

                for (String label : allLabels) {
                    commands.getDispatcher().getRoot().removeCommand(label);
                    commands.getDispatcher().getRoot().addChild(this.copyLiteralNode(label, commandNode));
                }
            }, 1);
        } else {
            this.autumn.getLogger().error("No command map present!");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ((CraftServer) Bukkit.getServer()).getServer().resources.managers().commands.sendCommands(((CraftPlayer) event.getPlayer()).getHandle());
    }

    private <S> LiteralCommandNode<S> copyLiteralNode(String alias, LiteralCommandNode<S> node) {
        LiteralCommandNode<S> copy = new LiteralCommandNode<>(alias, node.getCommand(), node.getRequirement(), node.getRedirect(), node.getRedirectModifier(), node.isFork());
        for (CommandNode<S> child : node.getChildren()) {
            copy.addChild(child);
        }
        return copy;
    }

    private CommandNode<CommandSourceStack> transformNode(CommandNode<Player> commandNode) {
        ArgumentBuilder<CommandSourceStack, ?> builder;

        if (commandNode instanceof LiteralCommandNode<Player> literalCommandNode) {
            builder = LiteralArgumentBuilder.literal(literalCommandNode.getLiteral());
        } else if (commandNode instanceof ArgumentCommandNode<Player, ?> argumentCommandNode) {
            RequiredArgumentBuilder<CommandSourceStack, ?> requiredArgumentBuilder = RequiredArgumentBuilder.argument(commandNode.getName(), argumentCommandNode.getType());
            builder = requiredArgumentBuilder;

            if (argumentCommandNode.getCustomSuggestions() == SUMMONABLE_ENTITIES) {
                requiredArgumentBuilder.suggests(SuggestionProviders.SUMMONABLE_ENTITIES);
            } else if (argumentCommandNode.getCustomSuggestions() != null) {
                requiredArgumentBuilder.suggests((context, suggestionsBuilder) -> {
                    return argumentCommandNode.listSuggestions(this.transformContext(context), suggestionsBuilder);
                });
            }
        } else {
            throw new IllegalStateException();
        }

        commandNode.getChildren().forEach(child -> {
            builder.then(this.transformNode(child));
        });

        Command<Player> command = commandNode.getCommand();
        if (command != null) {
            builder.executes(context -> {
                return command.run(this.transformContext(context));
            });
        }

        if (commandNode.getRedirect() != null) {
            if (commandNode.getRedirectModifier() != null) {
                throw new UnsupportedOperationException("Command has redirect modifier!");
            }
            builder.redirect(this.transformNode(commandNode.getRedirect()));
        }

        builder.requires(commandListenerWrapper -> {
            return commandListenerWrapper.getBukkitSender() instanceof Player && (commandNode.getRequirement() == null || commandNode.getRequirement().test((Player) commandListenerWrapper.getBukkitSender()));
        });

        return builder.build();
    }

    private CommandContext<Player> transformContext(CommandContext<CommandSourceStack> context) {
        CommandSender bukkitSender = context.getSource().getBukkitSender();
        if (!(bukkitSender instanceof Player player)) {
            throw new IllegalStateException();
        }

        Map<String, ParsedArgument<Player, ?>> transformedArguments = new HashMap<>();
        Map<String, ParsedArgument<CommandSourceStack, ?>> arguments = ReflectionField.getValuePrintException(CommandContext.class, context, "arguments");

        if (arguments != null) {
            arguments.forEach((key, argument) -> {
                transformedArguments.put(key, new ParsedArgument<>(argument.getRange().getStart(), argument.getRange().getEnd(), argument.getResult()));
            });
        }

        return new CommandContext<>(player, context.getInput(), transformedArguments, null, null, null, context.getRange(), context.getChild() == null ? null : this.transformContext(context.getChild()), null, context.isForked());
    }
}
