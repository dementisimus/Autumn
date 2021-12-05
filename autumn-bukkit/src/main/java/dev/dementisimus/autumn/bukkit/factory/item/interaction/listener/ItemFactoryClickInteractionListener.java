package dev.dementisimus.autumn.bukkit.factory.item.interaction.listener;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.namespace.ItemFactoryNamespace;
import dev.dementisimus.autumn.bukkit.factory.item.DefaultItemFactory;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.DefaultItemFactoryClickInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import dev.dementisimus.autumn.common.debug.SysOut;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class ItemFactoryClickInteractionListener @ BukkitAutumn
 *
 * @author dementisimus
 * @since 27.11.2021:23:17
 */
@AutumnListener
public class ItemFactoryClickInteractionListener implements Listener {

    public static final Map<String, AutumnCallback<ItemFactoryClickInteraction>> REQUESTED_INTERACTIONS = new HashMap<>();

    @EventHandler
    public void on(ValidInventoryClickEvent event) {
        if(InventoryFactory.isPlaceholder(event.getCurrentItem())) return;

        ItemFactory itemFactory = new DefaultItemFactory(event.getCurrentItem());
        String itemId = itemFactory.retrieve(ItemFactoryNamespace.NAMESPACE, ItemFactoryNamespace.ITEM_ID, PersistentDataType.STRING);
        AutumnCallback<ItemFactoryClickInteraction> clickInteractionCallback = REQUESTED_INTERACTIONS.get(itemId);

        if(itemId != null && clickInteractionCallback != null) {
            clickInteractionCallback.done(new DefaultItemFactoryClickInteraction(event, itemFactory));
        }
    }
}
