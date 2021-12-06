/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.inventory;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A factory for inventories
 *
 * @since 1.0.0
 */
public interface InventoryFactory {

    String PLACEHOLDER = " ";
    ItemStack AIR = new ItemStack(Material.AIR);

    /**
     * Sets the inventory contents
     *
     * @param itemStacks contents
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setContents(ItemStack[] itemStacks);

    /**
     * Sets an item (from an {@link ItemFactory} at a specific slot
     *
     * @param slot slot
     * @param itemFactory item factory
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setItem(int slot, ItemFactory itemFactory);

    /**
     * Sets an item at the specific slot
     *
     * @param slot slot
     * @param itemStack item stack
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setItem(int slot, ItemStack itemStack);

    /**
     * Adds items to the inventory
     *
     * @param itemStacks items
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory addItems(ItemStack... itemStacks);

    /**
     * Places Material placeholders
     *
     * @param placeholder placeholder material
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setPlaceholders(Material placeholder);

    /**
     * Places Material placeholders at specific slots
     *
     * @param placeholder placeholder material
     * @param slots slots
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setPlaceholders(Material placeholder, int... slots);

    /**
     * Sets air at specific slots
     *
     * @param slots slots
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setAir(int... slots);

    /**
     * Sets air from a slot to a slot
     *
     * @param from from slot
     * @param to to slot
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setAir(int from, int to);

    /**
     * Sets the max stack size of the inventory
     *
     * @param maxStackSize max stack size
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setMaxStackSize(int maxStackSize);

    /**
     * Sets the storage contents of the inventory
     *
     * @param itemStacks storage contents
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setStorageContents(ItemStack[] itemStacks);

    /**
     * Sets an item or a placeholder, if item is null
     *
     * @param slot slot
     * @param itemStack item
     * @param placeholder placeholder
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    InventoryFactory setItemOrPlaceholder(int slot, ItemStack itemStack, Material placeholder);

    /**
     * Gets an item at a specific slot
     *
     * @param slot slot
     *
     * @return the item at a specific slot, or null, if no item was found
     *
     * @since 1.0.0
     */
    ItemStack getItemAt(int slot);

    /**
     * Creates the inventory
     *
     * @return the inventory
     *
     * @since 1.0.0
     */
    Inventory create();

    /**
     * Creates the inventory for a player
     *
     * @param player player
     *
     * @since 1.0.0
     */
    void createFor(Player player);

    /**
     * Checks if the item stack is a placeholder
     *
     * @param itemStack item stack
     *
     * @return true, if the item stack is a placeholder, false otherwise
     *
     * @since 1.0.0
     */
    static boolean isPlaceholder(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta != null) {
            return itemMeta.getDisplayName().equals(PLACEHOLDER);
        }

        return false;
    }
}
