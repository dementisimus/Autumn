/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an item factory item click interaction
 *
 * @since 1.0.0
 */
public interface ItemFactoryClickInteraction extends ItemFactoryPlayerInteraction, ItemFactoryItemInteraction, ItemFactoryCancellableInteraction {

    /**
     * Gets the {@link ValidInventoryClickEvent}
     *
     * @return the {@link ValidInventoryClickEvent}
     *
     * @since 1.0.0
     */
    @NotNull ValidInventoryClickEvent event();

    /**
     * Gets the {@link InventoryView}
     *
     * @return the {@link InventoryView}
     *
     * @since 1.0.0
     */
    @NotNull InventoryView inventoryView();

    /**
     * Gets the {@link Inventory}
     *
     * @return the {@link Inventory}
     *
     * @since 1.0.0
     */
    @NotNull Inventory clickedInventory();

    /**
     * Checks if the click was a right-click
     *
     * @return true if the click was a right-click, false otherwise
     *
     * @since 1.0.0
     */
    boolean rightClick();

    /**
     * Checks if the click was a left-click
     *
     * @return true if the click was a left-click, false otherwise
     *
     * @since 1.0.0
     */
    boolean leftClick();

    /**
     * Checks if the click was a shift-click
     *
     * @return true if the click was a shift-click, false otherwise
     *
     * @since 1.0.0
     */
    boolean shiftClick();

    /**
     * Returns the slot the clicked item is in
     *
     * @return clicked slot
     *
     * @since 1.1.1
     */
    int slot();

    /**
     * Returns the slot type the clicked item is in
     *
     * @return clicked slot type
     *
     * @since 1.1.1
     */
    @NotNull InventoryType.SlotType slotType();
}
