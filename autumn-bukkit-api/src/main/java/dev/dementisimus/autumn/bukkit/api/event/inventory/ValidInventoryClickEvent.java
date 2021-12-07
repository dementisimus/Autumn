/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.event.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This event fires when a valid inventory click has occured
 *
 * @since 1.0.0
 */
public class ValidInventoryClickEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private InventoryClickEvent inventoryClickEvent;
    private Player player;
    private String title;
    private ItemStack currentItem;
    private String currentItemDisplayName;

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the inventory click event used by this event
     *
     * @return the inventory click event used by this event
     *
     * @since 1.0.0
     */
    public @NotNull InventoryClickEvent inventoryClickEvent() {
        return this.inventoryClickEvent;
    }

    /**
     * Sets the inventory click event used by this event
     *
     * @param inventoryClickEvent the inventory click event used by this event
     *
     * @since 1.0.0
     */
    public void inventoryClickEvent(@NotNull InventoryClickEvent inventoryClickEvent) {
        this.inventoryClickEvent = inventoryClickEvent;
    }

    /**
     * Gets the player who triggered the event
     *
     * @return the player who triggered the event
     *
     * @since 1.0.0
     */
    public @NotNull Player player() {
        return this.player;
    }

    /**
     * Sets the player who triggered the event
     *
     * @param player the player who triggered the event
     *
     * @since 1.0.0
     */
    public void player(@NotNull Player player) {
        this.player = player;
    }

    /**
     * Gets the title of the clicked inventory
     *
     * @return the title of the clicked inventory
     *
     * @since 1.0.0
     */
    public @NotNull String title() {
        return this.title;
    }

    /**
     * Sets the title of the clicked inventory
     *
     * @param title the title of the clicked inventory
     *
     * @since 1.0.0
     */
    public void title(@NotNull String title) {
        this.title = title;
    }

    /**
     * Gets the clicked item
     *
     * @return the clicked item
     *
     * @since 1.0.0
     */
    public @NotNull ItemStack currentItem() {
        return this.currentItem;
    }

    /**
     * Sets the clicked item
     *
     * @param currentItem the clicked item
     *
     * @since 1.0.0
     */
    public void currentItem(@NotNull ItemStack currentItem) {
        this.currentItem = currentItem;
    }

    /**
     * Gets the clicked item's display name
     *
     * @return the clicked item's display name
     *
     * @since 1.0.0
     */
    public @NotNull String currentItemDisplayName() {
        return this.currentItemDisplayName;
    }

    /**
     * Sets the clicked item's display name
     *
     * @param currentItemDisplayName the clicked item's display name
     *
     * @since 1.0.0
     */
    public void currentItemDisplayName(@NotNull String currentItemDisplayName) {
        this.currentItemDisplayName = currentItemDisplayName;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
