/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.event.inventory;

import lombok.Getter;
import lombok.Setter;
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

    @Getter @Setter private InventoryClickEvent inventoryClickEvent;
    @Getter @Setter private Player player;
    @Getter @Setter private String title;
    @Getter @Setter private ItemStack currentItem;
    @Getter @Setter private String currentItemDisplayName;

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
