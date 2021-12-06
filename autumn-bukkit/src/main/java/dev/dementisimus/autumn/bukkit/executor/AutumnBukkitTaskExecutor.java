/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.executor;

import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public record AutumnBukkitTaskExecutor(Plugin plugin) implements AutumnTaskExecutor {

    @Override
    public void synchronous(Runnable runnable) {
        Bukkit.getScheduler().runTask(this.plugin, runnable);
    }
}
