/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item.interaction.listener;

import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryDropInteraction;
import dev.dementisimus.autumn.bukkit.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.CustomItemFactoryDropInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnDoubleCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AutumnListener
public class ItemFactoryDropInteractionListener implements Listener {

    public static final Map<String, AutumnDoubleCallback<Player, ItemFactoryDropInteraction>> REQUESTED_INTERACTIONS = new HashMap<>();

    @EventHandler
    public void on(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();

        if(item.getItemMeta() != null && !REQUESTED_INTERACTIONS.isEmpty()) {
            CustomItemFactory itemFactory = CustomItemFactory.fromItemStack(item);

            if(itemFactory == null) return;

            String itemId = itemFactory.retrieveItemId();

            if(itemId != null) {
                if(!itemFactory.hasCooldown(player)) {
                    AutumnDoubleCallback<Player, ItemFactoryDropInteraction> interactionCallback = REQUESTED_INTERACTIONS.get(itemId);

                    if(interactionCallback != null) {
                        itemFactory.enableCooldown(player);
                        interactionCallback.done(player, new CustomItemFactoryDropInteraction(event, item, itemFactory));
                    }
                }else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
