/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item.interaction.listener;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.bukkit.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.CustomItemFactoryClickInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnBiCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

@AutumnListener
public class ItemFactoryClickInteractionListener implements Listener {

    public static final Map<String, AutumnBiCallback<Player, ItemFactoryClickInteraction>> REQUESTED_INTERACTIONS = new HashMap<>();

    @EventHandler
    public void on(ValidInventoryClickEvent event) {
        if(InventoryFactory.isPlaceholder(event.currentItem()) || REQUESTED_INTERACTIONS.isEmpty()) return;

        Player player = event.player();
        CustomItemFactory itemFactory = CustomItemFactory.fromItemStack(event.currentItem());

        if(itemFactory == null) return;

        String itemId = itemFactory.retrieveItemId();

        if(itemId != null && !itemFactory.hasCooldown(player)) {
            AutumnBiCallback<Player, ItemFactoryClickInteraction> clickInteractionCallback = REQUESTED_INTERACTIONS.get(itemId);

            if(clickInteractionCallback != null) {
                itemFactory.enableCooldown(player);
                clickInteractionCallback.done(player, new CustomItemFactoryClickInteraction(event, itemFactory));
            }
        }
    }
}
