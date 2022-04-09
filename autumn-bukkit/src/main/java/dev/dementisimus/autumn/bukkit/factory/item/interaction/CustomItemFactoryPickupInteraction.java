/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryPickupInteraction;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CustomItemFactoryPickupInteraction implements ItemFactoryPickupInteraction {

    private final EntityPickupItemEvent event;
    private final ItemStack item;
    private final ItemFactory itemFactory;

    @Override
    public boolean cancelled() {
        return this.event.isCancelled();
    }

    @Override
    public void cancelled(boolean cancelled) {
        this.event.setCancelled(cancelled);
    }

    @Override
    public @NotNull ItemStack item() {
        return this.item;
    }

    @Override
    public @NotNull ItemFactory itemFactory() {
        return this.itemFactory;
    }

    @Override
    public @NotNull EntityPickupItemEvent event() {
        return this.event;
    }

    @Override
    public @NotNull Player player() {
        return (Player) this.event.getEntity();
    }
}
