/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryDropInteraction;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CustomItemFactoryDropInteraction implements ItemFactoryDropInteraction {

    private final PlayerDropItemEvent dropItemEvent;
    private final ItemStack item;
    private final ItemFactory itemFactory;

    @Override
    public @NotNull PlayerDropItemEvent event() {
        return this.dropItemEvent;
    }

    @Override
    public @NotNull Player player() {
        return this.dropItemEvent.getPlayer();
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
    public boolean cancelled() {
        return this.dropItemEvent.isCancelled();
    }

    @Override
    public void cancelled(boolean cancelled) {
        this.dropItemEvent.setCancelled(cancelled);
    }
}
