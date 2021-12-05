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
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class ValidInventoryClickEvent @ BukkitAutumn
 *
 * @author dementisimus
 * @since 28.11.2021:12:33
 */
public class ValidInventoryClickEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Getter @Setter private InventoryClickEvent inventoryClickEvent;
    @Getter @Setter private Player player;
    @Getter @Setter private String title;
    @Getter @Setter private ItemStack currentItem;
    @Getter @Setter private String currentItemDisplayName;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

}
