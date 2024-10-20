/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.listener;

import dev.dementisimus.autumn.api.event.inventory.ValidInventoryClickEvent;
import dev.dementisimus.autumn.api.factory.inventory.InventoryFactory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack currentItem = event.getCurrentItem();

        if (clickedInventory != null) {
            if (currentItem != null && currentItem.getItemMeta() != null) {
                Component displayName = currentItem.getItemMeta().displayName();

                if (displayName == null) return;

                String currentItemDisplayName = LegacyComponentSerializer.legacySection().serialize(displayName);

                if (currentItemDisplayName.equalsIgnoreCase(InventoryFactory.PLACEHOLDER)) {
                    event.setCancelled(true);
                    return;
                }

                ValidInventoryClickEvent validInventoryClickEvent = new ValidInventoryClickEvent();

                validInventoryClickEvent.inventoryClickEvent(event);
                validInventoryClickEvent.player(player);
                validInventoryClickEvent.title(LegacyComponentSerializer.legacySection().serialize(event.getView().title()));
                validInventoryClickEvent.currentItem(currentItem);
                validInventoryClickEvent.currentItemDisplayName(currentItemDisplayName);
                validInventoryClickEvent.slot(event.getSlot());
                validInventoryClickEvent.slotType(event.getSlotType());

                Bukkit.getPluginManager().callEvent(validInventoryClickEvent);
            }
        }
    }
}
