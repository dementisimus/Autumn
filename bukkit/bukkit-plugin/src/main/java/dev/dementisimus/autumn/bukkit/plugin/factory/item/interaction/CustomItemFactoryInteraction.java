/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteraction;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class CustomItemFactoryInteraction implements ItemFactoryInteraction {

    private final PlayerInteractEvent interactEvent;
    private final ItemStack item;
    private final ItemFactory itemFactory;

    @Override
    public @NotNull PlayerInteractEvent event() {
        return this.interactEvent;
    }

    @Override
    public @NotNull Player player() {
        return this.interactEvent.getPlayer();
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
    public @NotNull Action action() {
        return this.interactEvent.getAction();
    }

    @Override
    public @Nullable EquipmentSlot slot() {
        return this.interactEvent.getHand();
    }

    @Override
    public @Nullable Block block() {
        return this.interactEvent.getClickedBlock();
    }

    @Override
    public @Nullable BlockFace blockFace() {
        return this.interactEvent.getBlockFace();
    }
}
