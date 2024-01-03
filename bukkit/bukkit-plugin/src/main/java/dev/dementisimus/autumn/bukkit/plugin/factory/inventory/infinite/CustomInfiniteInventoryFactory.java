/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.factory.inventory.infinite;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.infinite.InfiniteInventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.plugin.factory.inventory.CustomInventoryFactory;
import dev.dementisimus.autumn.bukkit.plugin.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
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
    private final int itemsInRow = 7;

    private final int inventoryRows;
    private final String titleTranslationProperty;
    private final int pageSize;
    private final Material placeholder;
    private final List<ItemStack> items = new ArrayList<>();
    private final InventoryFactory inventoryFactory;

    private Player createFor;
    private int itemCount;
    private int lastItemIndexPlaced;

    private AutumnTaskExecutor taskExecutor;

    private ItemStack replacedNextPageItem;
    private ItemStack replacedPreviousPageItem;

    public CustomInfiniteInventoryFactory(int inventoryRows, String titleTranslationProperty, Material placeholder) {
        this.inventoryRows = inventoryRows;
        this.titleTranslationProperty = titleTranslationProperty;
        this.placeholder = placeholder;

        this.pageSize = ((inventoryRows - 2) * this.itemsInRow);

        this.inventoryFactory = new CustomInventoryFactory(this.inventoryRows, this.titleTranslationProperty);
    }

    @Override
    public <T> void items(@NotNull List<T> items, @NotNull Class<T> clazz) {
        if (clazz.equals(Material.class)) {
            for (T item : items) this.items.add(new CustomItemFactory((Material) item).create());
        } else if (clazz.equals(ItemStack.class)) {
            this.items.addAll((Collection<? extends ItemStack>) items);
        } else {
            throw new IllegalStateException("Only Material or ItemStack may be used!");
        }

        this.itemCount = this.items.size();
    }

    @Override
    public void createFor(@NotNull Player createFor) {
        this.createFor = createFor;
    }

    @Override
    public InventoryFactory inventoryFactory() {
        return this.inventoryFactory;
    }

    @Override
    public void create(@NotNull AutumnTaskExecutor taskExecutor) {
        Preconditions.checkNotNull(this.createFor, "No player found to create an infinite inventory for!");

        this.taskExecutor = taskExecutor;

        List<ItemStack> pageItems = this.getPageItems();

        int slot = 10;
        int stopAt = this.previousPageItemSlot - 1;

        this.inventoryFactory.placeholderBorder(this.placeholder);

        for (ItemStack pageItem : pageItems) {
            if (slot == stopAt) break;

            slot = this.adjustSlot(slot);
            this.inventoryFactory.item(slot, pageItem);
            slot++;
        }

        if (slot == stopAt && pageItems.size() == this.pageSize) {
            if (this.replacedNextPageItem == null)
                this.replacedNextPageItem = this.inventoryFactory.itemAt(this.nextPageItemSlot);

            this.setPageMovementItem(Material.LIME_DYE, this.nextPageItemSlot, this.createFor, "autumn.infinite.inventory.next.page", this.lastItemIndexPlaced + pageItems.size());
        } else {
            this.inventoryFactory.itemOrPlaceholder(this.nextPageItemSlot, this.replacedNextPageItem, this.placeholder);
            this.replacedNextPageItem = null;
        }

        if (this.lastItemIndexPlaced > 0) {
            if (this.replacedPreviousPageItem == null)
                this.replacedPreviousPageItem = this.inventoryFactory.itemAt(this.previousPageItemSlot);

            this.setPageMovementItem(Material.RED_DYE, this.previousPageItemSlot, this.createFor, "autumn.infinite.inventory.previous.page", this.lastItemIndexPlaced - this.pageSize);
        } else {
            this.inventoryFactory.itemOrPlaceholder(this.previousPageItemSlot, this.replacedPreviousPageItem, this.placeholder);
            this.lastItemIndexPlaced = 0;
        }

        this.inventoryFactory.createFor(this.taskExecutor, this.createFor);
    }

    @Override
    public void create(@NotNull AutumnTaskExecutor taskExecutor, @NotNull Player openTo) {
        this.taskExecutor = taskExecutor;

        this.createFor(openTo);
        this.create(this.taskExecutor);
    }

    private int adjustSlot(int slot) {
        if (slot > 43) {
            slot--;
            return slot;
        }

        switch (slot) {
            case 17, 26, 35 -> slot += 2;
        }

        return slot;
    }

    private List<ItemStack> getPageItems() {
        List<ItemStack> pageItems;
        int toIndex = this.lastItemIndexPlaced + this.pageSize;

        if (toIndex > 0 && toIndex <= this.itemCount) {
            pageItems = this.copyItems(this.lastItemIndexPlaced, toIndex);

            if (pageItems.isEmpty()) {
                pageItems = this.copyItems(0, this.pageSize);
            }
        } else {
            pageItems = this.copyItems(this.lastItemIndexPlaced, this.itemCount);
        }

        return pageItems;
    }

    private void setPageMovementItem(Material material, int slot, Player player, String translationProperty, int newLastItemIndexPlaced) {
        ItemFactory itemFactory = new CustomItemFactory(material).displayName(player, translationProperty);
        this.inventoryFactory.item(slot, itemFactory);

        itemFactory.onClick((interactionPlayer, itemFactoryClickInteraction) -> {
            this.lastItemIndexPlaced = newLastItemIndexPlaced;
            this.create(this.taskExecutor);
        });
    }

    private List<ItemStack> copyItems(int fromIndex, int toIndex) {
        return new ArrayList<>(this.items.subList(fromIndex, toIndex));
    }
}
