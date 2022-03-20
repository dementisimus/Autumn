/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.inventory;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A factory for inventories
 *
 * @since 1.0.0
 */
public interface InventoryFactory {

    @NotNull String PLACEHOLDER = " ";
    @NotNull ItemStack AIR = new ItemStack(Material.AIR);

    /**
     * Sets the inventory contents
     *
     * @param itemStacks contents
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    @NotNull InventoryFactory contents(@NotNull ItemStack[] itemStacks);

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
    @NotNull InventoryFactory item(int slot, @NotNull ItemFactory itemFactory);

    /**
     * Sets an item (from an {@link ItemFactory} at specific slots
     *
     * @param itemFactory item factory
     * @param slots slots
     *
     * @return the inventory factory instance
     *
     * @since 1.1.1
     */
    @NotNull InventoryFactory item(@NotNull ItemFactory itemFactory, int... slots);

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
    @NotNull InventoryFactory item(int slot, @NotNull ItemStack itemStack);

    /**
     * Sets an item at specific slots
     *
     * @param itemStack item stack
     * @param slots slots
     *
     * @return the inventory factory instance
     *
     * @since 1.1.1
     */
    @NotNull InventoryFactory item(@NotNull ItemStack itemStack, int... slots);

    /**
     * Adds items to the inventory
     *
     * @param itemStacks items
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    @NotNull InventoryFactory items(@NotNull ItemStack... itemStacks);

    /**
     * Sets the translation property used for placeholders
     *
     * @param placeholderTranslationProperty translation property
     *
     * @return the inventory factory instance
     *
     * @since 1.1.1
     */
    @NotNull InventoryFactory placeholderTranslationProperty(String placeholderTranslationProperty);

    /**
     * Places Material placeholders
     *
     * @param placeholder placeholder material
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    @NotNull InventoryFactory placeholder(@NotNull Material placeholder);

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
    @NotNull InventoryFactory placeholder(@NotNull Material placeholder, int... slots);

    /**
     * Places Material placeholders as a border around the inventory
     *
     * @param placeholder placeholder material
     *
     * @return the inventory factory instance
     *
     * @since 1.1.1
     */
    @NotNull InventoryFactory placeholderBorder(@NotNull Material placeholder);

    /**
     * Sets air at specific slots
     *
     * @param slots slots
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    @NotNull InventoryFactory air(int... slots);

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
    @NotNull InventoryFactory air(int from, int to);

    /**
     * Sets the max stack size of the inventory
     *
     * @param maxStackSize max stack size
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    @NotNull InventoryFactory maxStackSize(int maxStackSize);

    /**
     * Sets the storage contents of the inventory
     *
     * @param itemStacks storage contents
     *
     * @return the inventory factory instance
     *
     * @since 1.0.0
     */
    @NotNull InventoryFactory storageContents(@NotNull ItemStack[] itemStacks);

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
    @NotNull InventoryFactory itemOrPlaceholder(int slot, @Nullable ItemStack itemStack, @NotNull Material placeholder);

    /**
     * Clears all items in the inner inventory
     *
     * @return the inventory factory instance
     *
     * @since 1.1.1
     */
    @NotNull InventoryFactory clearInnerItems();

    /**
     * Gets an item at a specific slot
     *
     * @param slot slot
     *
     * @return the item at a specific slot, or null, if no item was found
     *
     * @since 1.0.0
     */
    @Nullable ItemStack itemAt(int slot);

    /**
     * Creates the inventory
     *
     * @return the inventory
     *
     * @since 1.0.0
     */
    @NotNull Inventory create();

    /**
     * Creates the inventory for a player
     *
     * @param player player
     *
     * @since 1.0.0
     */
    void createFor(@NotNull Player player);

    /**
     * Creates the inventory for a player
     *
     * @param taskExecutor {@link AutumnTaskExecutor}
     * @param player {@link Player}
     *
     * @since 1.1.1
     */
    void createFor(@NotNull AutumnTaskExecutor taskExecutor, @NotNull Player player);

    /**
     * Checks if the item stack is a placeholder
     *
     * @param itemStack item stack
     *
     * @return true, if the item stack is a placeholder, false otherwise
     *
     * @since 1.0.0
     */
    static boolean isPlaceholder(@NotNull ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta != null) {
            return itemMeta.getDisplayName().equals(PLACEHOLDER);
        }

        return false;
    }
}
