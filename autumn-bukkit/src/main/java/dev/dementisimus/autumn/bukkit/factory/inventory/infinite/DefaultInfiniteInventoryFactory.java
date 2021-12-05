package dev.dementisimus.autumn.bukkit.factory.inventory.infinite;

import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.infinite.InfiniteInventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.factory.inventory.DefaultInventoryFactory;
import dev.dementisimus.autumn.bukkit.factory.item.DefaultItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultInfiniteInventoryFactory @ BukkitAutumn
 *
 * @author dementisimus
 * @since 25.11.2021:22:37
 */
public class DefaultInfiniteInventoryFactory implements InfiniteInventoryFactory {

    private final int nextPageItemSlot = 53;
    private final int previousPageItemSlot = 45;
    private final List<Player> createFor = new ArrayList<>();

    private final int inventoryRows;
    private final String titleTranslationProperty;
    private final int pageSize;
    private final Material placeholder;
    private final List<ItemStack> items = new ArrayList<>();
    private final InventoryFactory inventoryFactory;

    private int itemCount;
    private int lastItemIndexPlaced;

    private ItemStack replacedNextPageItem;
    private ItemStack replacedPreviousPageItem;

    public DefaultInfiniteInventoryFactory(int inventoryRows, String titleTranslationProperty, int pageSize, Material placeholder) {
        this.inventoryRows = inventoryRows;
        this.titleTranslationProperty = titleTranslationProperty;
        this.pageSize = pageSize;
        this.placeholder = placeholder;

        this.inventoryFactory = new DefaultInventoryFactory(this.inventoryRows, this.titleTranslationProperty);
        this.inventoryFactory.setPlaceholders(this.placeholder);
    }

    @Override
    public <T> void setItems(List<T> items, Class<T> clazz) {
        if(clazz.equals(Material.class)) {
            for(T item : items) this.items.add(new DefaultItemFactory((Material) item).create());
        }else if(clazz.equals(ItemStack.class)) {
            this.items.addAll((Collection<? extends ItemStack>) items);
        }else {
            throw new IllegalStateException("Only Material or ItemStack may be used!");
        }

        this.itemCount = items.size();
        this.lastItemIndexPlaced = 0;
    }

    @Override
    public void addCreateFor(Player player) {
        if(!this.createFor.contains(player)) this.createFor.add(player);
    }

    @Override
    public void create() {
        List<ItemStack> pageItems = this.getPageItems();

        int slot = 10;
        int stopAt = this.previousPageItemSlot - 1;

        this.inventoryFactory.setAir(10, 16);
        this.inventoryFactory.setAir(19, 25);
        this.inventoryFactory.setAir(28, 34);
        this.inventoryFactory.setAir(37, 43);

        for(ItemStack pageItem : pageItems) {
            if(slot == stopAt) break;

            slot = this.adjustSlot(slot);
            this.inventoryFactory.setItem(slot, pageItem);
            slot++;
        }

        if(slot == stopAt && pageItems.size() == this.pageSize) {
            if(this.replacedNextPageItem == null) this.replacedNextPageItem = this.inventoryFactory.getItemAt(this.nextPageItemSlot);

            this.setPageMovementItem(Material.LIME_DYE, this.nextPageItemSlot, "NEXT_PAGE", this.lastItemIndexPlaced + pageItems.size());
        }else {
            this.inventoryFactory.setItemOrPlaceholder(this.nextPageItemSlot, this.replacedNextPageItem, this.placeholder);
            this.replacedNextPageItem = null;
        }

        if(this.lastItemIndexPlaced > 0) {
            if(this.replacedPreviousPageItem == null) this.replacedPreviousPageItem = this.inventoryFactory.getItemAt(this.previousPageItemSlot);

            this.setPageMovementItem(Material.RED_DYE, this.previousPageItemSlot, "PREVIOUS_PAGE", this.lastItemIndexPlaced - this.pageSize);
        }else {
            this.inventoryFactory.setItemOrPlaceholder(this.previousPageItemSlot, this.replacedPreviousPageItem, this.placeholder);
            this.lastItemIndexPlaced = 0;
        }

        for(Player player : this.createFor) {
            this.inventoryFactory.createFor(player);
        }
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

    private void setPageMovementItem(Material material, int slot, String displayName, int newLastItemIndexPlaced) {
        ItemFactory itemFactory = new DefaultItemFactory(material).displayName(displayName);
        this.inventoryFactory.setItem(slot, itemFactory);

        itemFactory.onClick(itemFactoryClickInteraction -> {
            this.lastItemIndexPlaced = newLastItemIndexPlaced;
            this.create();
        });
    }

    private List<ItemStack> copyItems(int fromIndex, int toIndex) {
        return new ArrayList<>(this.items.subList(fromIndex, toIndex));
    }
}