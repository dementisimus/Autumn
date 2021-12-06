/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/**
 * The event fired when a click has been issued on an item factory item
 *
 * @since 1.0.0
 */
public interface ItemFactoryClickInteraction {

    /**
     * Gets the {@link ValidInventoryClickEvent}
     *
     * @return the {@link ValidInventoryClickEvent}
     *
     * @since 1.0.0
     */
    ValidInventoryClickEvent event();

    /**
     * Gets the {@link InventoryView}
     *
     * @return the {@link InventoryView}
     *
     * @since 1.0.0
     */
    InventoryView inventoryView();

    /**
     * Gets the {@link Inventory}
     *
     * @return the {@link Inventory}
     *
     * @since 1.0.0
     */
    Inventory clickedInventory();

    /**
     * Gets the {@link Player} who clicked on the item
     *
     * @return the {@link Player} who clicked on the item
     *
     * @since 1.0.0
     */
    Player player();

    /**
     * Gets the {@link ItemStack} on which has been clicked on
     *
     * @return the {@link ItemStack} on which has been clicked on
     *
     * @since 1.0.0
     */
    ItemStack item();

    /**
     * Gets the {@link ItemFactory} from {@link #item()}
     *
     * @return the {@link ItemFactory} from {@link #item()}
     *
     * @since 1.0.0
     */
    ItemFactory itemFactory();

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
}
