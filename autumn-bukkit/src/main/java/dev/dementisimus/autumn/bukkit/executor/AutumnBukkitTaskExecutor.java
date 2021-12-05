package dev.dementisimus.autumn.bukkit.executor;

import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnBukkitTaskExecutor @ BukkitAutumn
 *
 * @author dementisimus
 * @since 24.11.2021:18:48
 */
public record AutumnBukkitTaskExecutor(Plugin plugin) implements AutumnTaskExecutor {

    @Override
    public void synchronous(Runnable runnable) {
        Bukkit.getScheduler().runTask(this.plugin, runnable);
    }
}
