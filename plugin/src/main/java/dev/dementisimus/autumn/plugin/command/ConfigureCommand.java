/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.dementisimus.autumn.api.npc.AutumnNPC;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.npc.CustomAutumnNPC;
import dev.dementisimus.autumn.plugin.util.ItemStackSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ConfigureCommand extends CustomAutumnCommand {

    private final CustomAutumn autumn;

    public ConfigureCommand(CustomAutumn autumn) {
        super("autumn", "autumn.admin");

        this.autumn = autumn;
    }

    @Override
    public void register(LiteralArgumentBuilder<Player> literalArgumentBuilder) {
        literalArgumentBuilder.then(this.literal("reload").then(this.literal("config-npcs").executes(context -> {
            for (AutumnNPC autumnNPC : new ArrayList<AutumnNPC>() {{
                this.addAll(ConfigureCommand.this.autumn.getNpcPool().getNpcsByEntityId().values());
            }}) {
                if (((CustomAutumnNPC) autumnNPC).isConfigNPC()) {
                    this.autumn.getNpcPool().unregister(autumnNPC);
                }
            }

            this.autumn.getNpcPool().loadConfigNPCs();
            this.autumn.getNpcPool().doNPCCheck(context.getSource(), null, context.getSource().getLocation());

            context.getSource().sendMessage("Config-NPC reload success");
            return 1;
        }))).then(this.literal("dump").then(this.literal("item").executes(context -> {
            String itemAsString = ItemStackSerializer.itemToString(context.getSource().getInventory().getItemInMainHand()).replace('"', '\'');
            context.getSource().sendMessage(Component.text(itemAsString).color(NamedTextColor.GRAY).clickEvent(ClickEvent.copyToClipboard(itemAsString)));
            return 1;
        })));
    }
}
