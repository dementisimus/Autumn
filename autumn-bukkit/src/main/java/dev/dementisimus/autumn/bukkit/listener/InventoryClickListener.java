/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.listener;

import dev.dementisimus.autumn.bukkit.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@AutumnListener
public class InventoryClickListener implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack currentItem = event.getCurrentItem();

        if(clickedInventory != null) {
            if(clickedInventory.equals(player.getInventory())) {
                event.setCancelled(false);
                return;
            }

            event.setCancelled(true);

            if(currentItem != null && currentItem.getItemMeta() != null) {
                String title = event.getView().getTitle();
                String currentItemDisplayName = currentItem.getItemMeta().getDisplayName();

                ValidInventoryClickEvent validInventoryClickEvent = new ValidInventoryClickEvent();

                validInventoryClickEvent.inventoryClickEvent(event);
                validInventoryClickEvent.player(player);
                validInventoryClickEvent.title(title);
                validInventoryClickEvent.currentItem(currentItem);
                validInventoryClickEvent.currentItemDisplayName(currentItemDisplayName);

                Bukkit.getPluginManager().callEvent(validInventoryClickEvent);
            }
        }
    }
}
