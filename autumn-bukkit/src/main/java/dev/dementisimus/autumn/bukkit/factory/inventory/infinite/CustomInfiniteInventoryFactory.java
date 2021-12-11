/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.inventory.infinite;

import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.infinite.InfiniteInventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.factory.inventory.CustomInventoryFactory;
import dev.dementisimus.autumn.bukkit.factory.item.CustomItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomInfiniteInventoryFactory implements InfiniteInventoryFactory {

    private final int nextPageItemSlot = 53;
    private final int previousPageItemSlot = 45;

    private final int inventoryRows;
    private final String titleTranslationProperty;
    private final int pageSize;
    private final Material placeholder;
    private final List<ItemStack> items = new ArrayList<>();
    private final InventoryFactory inventoryFactory;

    private Player createFor;
    private int itemCount;
    private int lastItemIndexPlaced;

    private ItemStack replacedNextPageItem;
    private ItemStack replacedPreviousPageItem;

    public CustomInfiniteInventoryFactory(int inventoryRows, String titleTranslationProperty, int pageSize, Material placeholder) {
        this.inventoryRows = inventoryRows;
        this.titleTranslationProperty = titleTranslationProperty;
        this.pageSize = pageSize;
        this.placeholder = placeholder;

        this.inventoryFactory = new CustomInventoryFactory(this.inventoryRows, this.titleTranslationProperty);
        this.inventoryFactory.placeholder(this.placeholder);
    }

    @Override
    public <T> void items(@NotNull List<T> items, @NotNull Class<T> clazz) {
        if(clazz.equals(Material.class)) {
            for(T item : items) this.items.add(new CustomItemFactory((Material) item).create());
        }else if(clazz.equals(ItemStack.class)) {
            this.items.addAll((Collection<? extends ItemStack>) items);
        }else {
            throw new IllegalStateException("Only Material or ItemStack may be used!");
        }

        this.itemCount = items.size();
        this.lastItemIndexPlaced = 0;
    }

    @Override
    public void createFor(@NotNull Player createFor) {
        this.createFor = createFor;
    }

    @Override
    public void create() {
        List<ItemStack> pageItems = this.getPageItems();

        int slot = 10;
        int stopAt = this.previousPageItemSlot - 1;

        this.inventoryFactory.air(10, 16);
        this.inventoryFactory.air(19, 25);
        this.inventoryFactory.air(28, 34);
        this.inventoryFactory.air(37, 43);

        for(ItemStack pageItem : pageItems) {
            if(slot == stopAt) break;

            slot = this.adjustSlot(slot);
            this.inventoryFactory.item(slot, pageItem);
            slot++;
        }

        if(slot == stopAt && pageItems.size() == this.pageSize) {
            if(this.replacedNextPageItem == null)
                this.replacedNextPageItem = this.inventoryFactory.itemAt(this.nextPageItemSlot);

            this.setPageMovementItem(Material.LIME_DYE, this.nextPageItemSlot, this.createFor, "autumn.infinite.inventory.next.page", this.lastItemIndexPlaced + pageItems.size());
        }else {
            this.inventoryFactory.itemOrPlaceholder(this.nextPageItemSlot, this.replacedNextPageItem, this.placeholder);
            this.replacedNextPageItem = null;
        }

        if(this.lastItemIndexPlaced > 0) {
            if(this.replacedPreviousPageItem == null)
                this.replacedPreviousPageItem = this.inventoryFactory.itemAt(this.previousPageItemSlot);

            this.setPageMovementItem(Material.RED_DYE, this.previousPageItemSlot, this.createFor, "autumn.infinite.inventory.previous.page", this.lastItemIndexPlaced - this.pageSize);
        }else {
            this.inventoryFactory.itemOrPlaceholder(this.previousPageItemSlot, this.replacedPreviousPageItem, this.placeholder);
            this.lastItemIndexPlaced = 0;
        }

        this.inventoryFactory.createFor(this.createFor);
    }

    private int adjustSlot(int slot) {
        if(slot > 43) {
            slot--;
            return slot;
        }

        switch(slot) {
            case 17, 26, 35 -> slot += 2;
        }

        return slot;
    }

    private List<ItemStack> getPageItems() {
        List<ItemStack> pageItems;
        int toIndex = this.lastItemIndexPlaced + this.pageSize;

        if(toIndex > 0 && toIndex <= this.itemCount) {
            pageItems = this.copyItems(this.lastItemIndexPlaced, toIndex);

            if(pageItems.isEmpty()) {
                pageItems = this.copyItems(0, this.pageSize);
            }
        }else {
            pageItems = this.copyItems(this.lastItemIndexPlaced, this.itemCount);
        }

        return pageItems;
    }

    private void setPageMovementItem(Material material, int slot, Player player, String translationProperty, int newLastItemIndexPlaced) {
        ItemFactory itemFactory = new CustomItemFactory(material).displayName(player, translationProperty);
        this.inventoryFactory.item(slot, itemFactory);

        itemFactory.onClick(itemFactoryClickInteraction -> {
            this.lastItemIndexPlaced = newLastItemIndexPlaced;
            this.create();
        });
    }

    private List<ItemStack> copyItems(int fromIndex, int toIndex) {
        return new ArrayList<>(this.items.subList(fromIndex, toIndex));
    }
}
