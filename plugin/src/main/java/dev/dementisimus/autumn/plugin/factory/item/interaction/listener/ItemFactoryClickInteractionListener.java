/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.factory.item.interaction.listener;

import dev.dementisimus.autumn.api.callback.DoubleCallback;
import dev.dementisimus.autumn.api.callback.QuadrupleCallback;
import dev.dementisimus.autumn.api.callback.TripleCallback;
import dev.dementisimus.autumn.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.api.factory.item.interaction.ItemFactoryInteractionEntry;
import dev.dementisimus.autumn.plugin.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.plugin.factory.item.interaction.CustomItemFactoryClickInteraction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class ItemFactoryClickInteractionListener implements Listener {

    public static final Map<String, ItemFactoryInteractionEntry> CLICK_INTERACTION_ENTRIES = new HashMap<>();

    @EventHandler
    public void on(ValidInventoryClickEvent event) {
        if (InventoryFactory.isPlaceholder(event.currentItem()) || CLICK_INTERACTION_ENTRIES.isEmpty()) return;

        Player player = event.player();
        CustomItemFactory itemFactory = CustomItemFactory.fromItemStack(event.currentItem());

        if (itemFactory == null) return;

        String itemId = itemFactory.retrieveItemId();

        if (itemId != null && !itemFactory.hasCooldown(player)) {
            ItemFactoryInteractionEntry interactionEntry = CLICK_INTERACTION_ENTRIES.get(itemId);

            if (interactionEntry != null) {
                ItemFactoryClickInteraction clickInteraction = new CustomItemFactoryClickInteraction(event, itemFactory);
                Object namespaceKeyEntry = interactionEntry.preCall(player, itemFactory);

                DoubleCallback<Player, ItemFactoryClickInteraction> clickInteractionCallback = interactionEntry.clickInteractionCallback();
                TripleCallback<Player, ItemFactoryClickInteraction, Object> retrieveOnClickInteractionCallback = interactionEntry.retrieveOnClickInteractionCallback();
                QuadrupleCallback<Player, ItemFactoryClickInteraction, ItemFactory, Object> retrieveOnClickInteractionFactoryCallback = interactionEntry.retrieveOnClickInteractionFactoryCallback();

                if (clickInteractionCallback != null) {
                    clickInteractionCallback.done(player, clickInteraction);
                    event.inventoryClickEvent().setCancelled(true);
                } else if (retrieveOnClickInteractionCallback != null) {
                    retrieveOnClickInteractionCallback.done(player, clickInteraction, namespaceKeyEntry);
                    event.inventoryClickEvent().setCancelled(true);
                } else if (retrieveOnClickInteractionFactoryCallback != null) {
                    retrieveOnClickInteractionFactoryCallback.done(player, clickInteraction, itemFactory, namespaceKeyEntry);
                    event.inventoryClickEvent().setCancelled(true);
                }
            }
        }
    }
}
