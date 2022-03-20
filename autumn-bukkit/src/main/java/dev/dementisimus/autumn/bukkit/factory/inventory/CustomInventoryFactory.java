/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.inventory;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.bukkit.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.bukkit.i18n.CustomBukkitTranslation;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomInventoryFactory implements InventoryFactory {

    private final Inventory inventory;

    private Player player;
    private String placeholderTranslationProperty;

    public CustomInventoryFactory(int rows, String title) {
        this.checkRowArgument(rows);

        this.inventory = Bukkit.createInventory(null, rows * 9, title);
    }

    public CustomInventoryFactory(int rows, Player player, String translationProperty, AutumnTranslationReplacement... replacements) {
        this.checkRowArgument(rows);

        this.player = player;

        AutumnBukkitTranslation bukkitTranslation = new CustomBukkitTranslation(translationProperty);
        bukkitTranslation.replacement(replacements);

        this.inventory = Bukkit.createInventory(null, rows * 9, bukkitTranslation.get(player));
    }

    public CustomInventoryFactory(InventoryType inventoryType) {
        this.inventory = Bukkit.createInventory(null, inventoryType);
    }

    public CustomInventoryFactory(InventoryType inventoryType, String title) {
        this.inventory = Bukkit.createInventory(null, inventoryType, title);
    }

    @Override
    public @NotNull InventoryFactory contents(@NotNull ItemStack[] itemStacks) {
        this.inventory.setContents(itemStacks);
        return this;
    }

    @Override
    public @NotNull InventoryFactory item(int slot, @NotNull ItemFactory itemFactory) {
        return this.item(slot, itemFactory.create());
    }

    @Override
    public @NotNull InventoryFactory item(@NotNull ItemFactory itemFactory, int... slots) {
        for(int slot : slots) {
            this.item(slot, itemFactory);
        }
        return this;
    }

    @Override
    public @NotNull InventoryFactory item(int slot, @NotNull ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
        return this;
    }

    @Override
    public @NotNull InventoryFactory item(@NotNull ItemStack itemStack, int... slots) {
        for(int slot : slots) {
            this.item(slot, itemStack);
        }

        return this;
    }

    @Override
    public @NotNull InventoryFactory items(@NotNull ItemStack... itemStacks) {
        this.inventory.addItem(itemStacks);
        return this;
    }

    @Override
    public @NotNull InventoryFactory placeholderTranslationProperty(String placeholderTranslationProperty) {
        this.placeholderTranslationProperty = placeholderTranslationProperty;
        return this;
    }

    @Override
    public @NotNull InventoryFactory placeholder(@NotNull Material placeholder) {
        List<Integer> slots = new ArrayList<>();

        for(int slot = 0; slot < this.inventory.getSize(); slot++) {
            slots.add(slot);
        }

        return this.placeholder(placeholder, slots.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public @NotNull InventoryFactory placeholder(@NotNull Material placeholder, int... slots) {
        ItemFactory itemFactory = new CustomItemFactory(placeholder);

        if(this.placeholderTranslationProperty != null && this.player != null) {
            itemFactory.displayName(this.player, this.placeholderTranslationProperty);
        }else {
            itemFactory.displayName(PLACEHOLDER);
        }

        ItemStack placeholderItemStack = itemFactory.create();

        for(int slot : slots) {
            this.item(slot, placeholderItemStack);
        }

        return this;
    }

    @Override
    public @NotNull InventoryFactory placeholderBorder(@NotNull Material placeholder) {
        this.placeholder(placeholder);
        this.clearInnerItems();

        return this;
    }

    @Override
    public @NotNull InventoryFactory air(int... slots) {
        for(int slot : slots) {
            this.item(slot, AIR);
        }
        return this;
    }

    @Override
    public @NotNull InventoryFactory air(int from, int to) {
        for(int slot = from; slot < to + 1; slot++) {
            this.item(slot, AIR);
        }
        return this;
    }

    @Override
    public @NotNull InventoryFactory maxStackSize(int maxStackSize) {
        this.inventory.setMaxStackSize(maxStackSize);
        return this;
    }

    @Override
    public @NotNull InventoryFactory storageContents(@NotNull ItemStack[] itemStacks) {
        this.inventory.setStorageContents(itemStacks);
        return this;
    }

    @Override
    public @NotNull InventoryFactory itemOrPlaceholder(int slot, @Nullable ItemStack itemStack, @NotNull Material placeholder) {
        if(itemStack == null) {
            this.placeholder(placeholder, slot);
        }else {
            this.item(slot, itemStack);
        }

        return this;
    }

    @Override
    public @NotNull InventoryFactory clearInnerItems() {
        switch(this.inventory.getSize()) {
            case 27 -> {
                this.air(10, 16);
            }
            case 36 -> {
                this.air(10, 16);
                this.air(19, 25);
            }
            case 45 -> {
                this.air(10, 16);
                this.air(19, 25);
                this.air(28, 34);
            }
            case 54 -> {
                this.air(10, 16);
                this.air(19, 25);
                this.air(28, 34);
                this.air(37, 43);
            }
        }

        return this;
    }

    @Override
    public @Nullable ItemStack itemAt(int slot) {
        return this.inventory.getItem(slot);
    }

    @Override
    public @NotNull Inventory create() {
        return this.inventory;
    }

    @Override
    public void createFor(@NotNull Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public void createFor(@NotNull AutumnTaskExecutor taskExecutor, @NotNull Player player) {
        taskExecutor.synchronous(() -> player.openInventory(this.inventory));
    }

    private void checkRowArgument(int rows) {
        Preconditions.checkArgument(rows <= 6, "InventoryRows may not be smaller than 1 (=9 slots) and greater than 6 (6*9=54 slots)!");
    }
}
