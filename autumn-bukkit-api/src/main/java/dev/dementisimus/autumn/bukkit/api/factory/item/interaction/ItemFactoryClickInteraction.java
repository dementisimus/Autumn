package dev.dementisimus.autumn.bukkit.api.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class ItemFactoryClickInteraction @ BukkitAutumn
 *
 * @author dementisimus
 * @since 27.11.2021:23:05
 */
public interface ItemFactoryClickInteraction {

    ValidInventoryClickEvent event();

    InventoryView inventoryView();

    Inventory clickedInventory();

    Player player();

    ItemStack item();

    ItemFactory itemFactory();

    boolean rightClick();

    boolean leftClick();

    boolean shiftClick();
}
