package dev.dementisimus.autumn.bukkit.api.factory.inventory;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class InventoryFactory @ BukkitAutumn
 *
 * @author dementisimus
 * @since 25.11.2021:22:16
 */
public interface InventoryFactory {

    String PLACEHOLDER = " ";
    ItemStack AIR = new ItemStack(Material.AIR);

    static boolean isPlaceholder(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta != null) {
            return itemMeta.getDisplayName().equals(PLACEHOLDER);
        }

        return false;
    }

    InventoryFactory setContents(ItemStack[] itemStacks);

    InventoryFactory setItem(int slot, ItemFactory itemFactory);

    InventoryFactory setItem(int slot, ItemStack itemStack);

    InventoryFactory addItems(ItemStack... itemStacks);

    InventoryFactory setPlaceholders(Material placeholder);

    InventoryFactory setPlaceholders(Material placeholder, int... slots);

    InventoryFactory setAir(int... slots);

    InventoryFactory setAir(int from, int to);

    InventoryFactory setMaxStackSize(int maxStackSize);

    InventoryFactory setStorageContents(ItemStack[] itemStacks);

    InventoryFactory setItemOrPlaceholder(int slot, ItemStack itemStack, Material placeholder);

    ItemStack getItemAt(int slot);

    Inventory create();

    void createFor(Player player);
}
