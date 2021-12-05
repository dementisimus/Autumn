package dev.dementisimus.autumn.bukkit.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultItemFactoryClickInteraction @ BukkitAutumn
 *
 * @author dementisimus
 * @since 27.11.2021:23:06
 */
public class DefaultItemFactoryClickInteraction implements ItemFactoryClickInteraction {

    private final ValidInventoryClickEvent validInventoryClickEvent;
    private final InventoryClickEvent inventoryClickEvent;
    private final ItemFactory itemFactory;

    public DefaultItemFactoryClickInteraction(ValidInventoryClickEvent validInventoryClickEvent, ItemFactory itemFactory) {
        this.validInventoryClickEvent = validInventoryClickEvent;
        this.itemFactory = itemFactory;
        this.inventoryClickEvent = this.validInventoryClickEvent.getInventoryClickEvent();
    }

    @Override
    public ValidInventoryClickEvent event() {
        return this.validInventoryClickEvent;
    }

    @Override
    public InventoryView inventoryView() {
        return this.inventoryClickEvent.getView();
    }

    @Override
    public Inventory clickedInventory() {
        return this.inventoryClickEvent.getClickedInventory();
    }

    @Override
    public Player player() {
        return (Player) this.inventoryClickEvent.getWhoClicked();
    }

    @Override
    public ItemStack item() {
        return this.inventoryClickEvent.getCurrentItem();
    }

    @Override
    public ItemFactory itemFactory() {
        return this.itemFactory;
    }

    @Override
    public boolean rightClick() {
        return this.inventoryClickEvent.isRightClick();
    }

    @Override
    public boolean leftClick() {
        return this.inventoryClickEvent.isLeftClick();
    }

    @Override
    public boolean shiftClick() {
        return this.inventoryClickEvent.isShiftClick();
    }
}
