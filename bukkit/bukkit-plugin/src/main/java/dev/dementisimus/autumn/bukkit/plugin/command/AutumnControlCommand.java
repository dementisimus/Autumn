/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.command;

import com.google.inject.Inject;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.dementisimus.autumn.bukkit.api.command.AutumnBrigadierCommand;
import dev.dementisimus.autumn.bukkit.api.npc.AutumnNPC;
import dev.dementisimus.autumn.bukkit.plugin.BukkitAutumn;
import dev.dementisimus.autumn.bukkit.plugin.npc.CustomAutumnNPC;
import dev.dementisimus.autumn.bukkit.plugin.npc.pool.CustomAutumnNPCPool;
import dev.dementisimus.autumn.bukkit.plugin.util.AutumnItemStackSerializerUtil;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@AutumnCommand(name = "autumn", permissions = "autumn.admin")
public class AutumnControlCommand implements AutumnBrigadierCommand {

    @Inject
    BukkitAutumn bukkitAutumn;

    @Override
    public void register(LiteralArgumentBuilder<Player> literalArgumentBuilder) {
        literalArgumentBuilder.then(this.literal("reload").then(this.literal("config-npcs").executes(context -> {
            for (AutumnNPC autumnNPC : new ArrayList<AutumnNPC>() {{
                this.addAll(((CustomAutumnNPCPool) AutumnControlCommand.this.bukkitAutumn.getNpcPool()).getNpcsByEntityId().values());
            }}) {
                if (((CustomAutumnNPC) autumnNPC).isConfigNPC()) {
                    this.bukkitAutumn.getNpcPool().unregister(autumnNPC);
                }
            }

            ((CustomAutumnNPCPool) this.bukkitAutumn.getNpcPool()).loadConfigNPCs();
            ((CustomAutumnNPCPool) this.bukkitAutumn.getNpcPool()).doNPCCheck(context.getSource(), null, context.getSource().getLocation());

            context.getSource().sendMessage("Config-NPC reload success");
            return 1;
        }))).then(this.literal("dump").then(this.literal("item").executes(context -> {
            String itemAsString = AutumnItemStackSerializerUtil.itemToString(context.getSource().getInventory().getItemInMainHand()).replace('"', '\'');
            context.getSource().sendMessage(Component.text(itemAsString).color(NamedTextColor.GRAY).clickEvent(ClickEvent.copyToClipboard(itemAsString)));
            return 1;
        })));
    }
}
