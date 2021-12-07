/*
 | Copyright 2021 dementisimus,
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
import dev.dementisimus.autumn.bukkit.factory.item.DefaultItemFactory;
import dev.dementisimus.autumn.bukkit.i18n.DefaultAutumnBukkitTranslation;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultInventoryFactory implements InventoryFactory {

    private final Inventory inventory;

    public DefaultInventoryFactory(int rows, String title) {
        this.checkRowArgument(rows);

        this.inventory = Bukkit.createInventory(null, rows * 9, title);
    }

    public DefaultInventoryFactory(int rows, Player player, String translationProperty, AutumnTranslationReplacement... replacements) {
        this.checkRowArgument(rows);

        AutumnBukkitTranslation bukkitTranslation = new DefaultAutumnBukkitTranslation(translationProperty);
        bukkitTranslation.replacement(replacements);

        this.inventory = Bukkit.createInventory(null, rows * 9, bukkitTranslation.get(player));
    }

    public DefaultInventoryFactory(InventoryType inventoryType) {
        this.inventory = Bukkit.createInventory(null, inventoryType);
    }

    public DefaultInventoryFactory(InventoryType inventoryType, String title) {
        this.inventory = Bukkit.createInventory(null, inventoryType, title);
    }

    @Override
    public @NotNull InventoryFactory setContents(@NotNull ItemStack[] itemStacks) {
        this.inventory.setContents(itemStacks);
        return this;
    }

    @Override
    public @NotNull InventoryFactory setItem(int slot, @NotNull ItemFactory itemFactory) {
        return this.setItem(slot, itemFactory.create());
    }

    @Override
    public @NotNull InventoryFactory setItem(int slot, @NotNull ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
        return this;
    }

    @Override
    public @NotNull InventoryFactory addItems(@NotNull ItemStack... itemStacks) {
        this.inventory.addItem(itemStacks);
        return this;
    }

    @Override
    public @NotNull InventoryFactory setPlaceholders(@NotNull Material placeholder) {
        ItemStack placeholderItemStack = new DefaultItemFactory(placeholder).displayName(PLACEHOLDER).create();

        for(int slot = 0; slot < this.inventory.getSize(); slot++) {
            this.setItem(slot, placeholderItemStack);
        }

        return this;
    }

    @Override
    public @NotNull InventoryFactory setPlaceholders(@NotNull Material placeholder, int... slots) {
        ItemStack placeholderItemStack = new DefaultItemFactory(placeholder).displayName(PLACEHOLDER).create();

        for(int slot : slots) {
            this.setItem(slot, placeholderItemStack);
        }

        return this;
    }

    @Override
    public @NotNull InventoryFactory setAir(int... slots) {
        for(int slot : slots) {
            this.setItem(slot, AIR);
        }
        return this;
    }

    @Override
    public @NotNull InventoryFactory setAir(int from, int to) {
        for(int slot = from; slot < to + 1; slot++) {
            this.setItem(slot, AIR);
        }
        return this;
    }

    @Override
    public @NotNull InventoryFactory setMaxStackSize(int maxStackSize) {
        this.inventory.setMaxStackSize(maxStackSize);
        return this;
    }

    @Override
    public @NotNull InventoryFactory setStorageContents(@NotNull ItemStack[] itemStacks) {
        this.inventory.setStorageContents(itemStacks);
        return this;
    }

    @Override
    public @NotNull InventoryFactory setItemOrPlaceholder(int slot, @Nullable ItemStack itemStack, @NotNull Material placeholder) {
        if(itemStack == null) {
            this.setPlaceholders(placeholder, slot);
        }else {
            this.setItem(slot, itemStack);
        }

        return this;
    }

    @Override
    public @Nullable ItemStack getItemAt(int slot) {
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

    private void checkRowArgument(int rows) {
        Preconditions.checkArgument(rows <= 6, "InventoryRows may not be smaller than 1 (=9 slots) and greater than 6 (6*9=54 slots)!");
    }
}
