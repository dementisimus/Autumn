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
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.namespace.ItemFactoryNamespace;
import dev.dementisimus.autumn.bukkit.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.CustomItemFactoryClickInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

@AutumnListener
public class ItemFactoryClickInteractionListener implements Listener {

    public static final Map<String, AutumnCallback<ItemFactoryClickInteraction>> REQUESTED_INTERACTIONS = new HashMap<>();

    @EventHandler
    public void on(ValidInventoryClickEvent event) {
        if(InventoryFactory.isPlaceholder(event.currentItem())) return;

        ItemFactory itemFactory = new CustomItemFactory(event.currentItem());
        String itemId = itemFactory.retrieve(ItemFactoryNamespace.NAMESPACE, ItemFactoryNamespace.ITEM_ID, PersistentDataType.STRING);

        if(itemId != null) {
            AutumnCallback<ItemFactoryClickInteraction> clickInteractionCallback = REQUESTED_INTERACTIONS.get(itemId);

            if(clickInteractionCallback != null) {
                clickInteractionCallback.done(new CustomItemFactoryClickInteraction(event, itemFactory));
            }
        }
    }
}
