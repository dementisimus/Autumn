/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DefaultItemFactoryClickInteraction implements ItemFactoryClickInteraction {

    private final ValidInventoryClickEvent validInventoryClickEvent;
    private final InventoryClickEvent inventoryClickEvent;
    private final ItemFactory itemFactory;

    public DefaultItemFactoryClickInteraction(ValidInventoryClickEvent validInventoryClickEvent, ItemFactory itemFactory) {
        this.validInventoryClickEvent = validInventoryClickEvent;
        this.itemFactory = itemFactory;
        this.inventoryClickEvent = this.validInventoryClickEvent.inventoryClickEvent();
    }

    @Override
    public @NotNull ValidInventoryClickEvent event() {
        return this.validInventoryClickEvent;
    }

    @Override
    public @NotNull InventoryView inventoryView() {
        return this.inventoryClickEvent.getView();
    }

    @Override
    public @NotNull Inventory clickedInventory() {
        return this.inventoryClickEvent.getClickedInventory();
    }

    @Override
    public @NotNull Player player() {
        return (Player) this.inventoryClickEvent.getWhoClicked();
    }

    @Override
    public @NotNull ItemStack item() {
        return this.inventoryClickEvent.getCurrentItem();
    }

    @Override
    public @NotNull ItemFactory itemFactory() {
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
