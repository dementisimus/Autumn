package dev.dementisimus.autumn.bukkit.api.factory.inventory.infinite;

import org.bukkit.entity.Player;

import java.util.List;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class InfiniteInventoryFactory @ BukkitAutumn
 *
 * @author dementisimus
 * @since 25.11.2021:22:37
 */
public interface InfiniteInventoryFactory {

    <T> void setItems(List<T> items, Class<T> clazz);

    void addCreateFor(Player player);

    void create();
}
