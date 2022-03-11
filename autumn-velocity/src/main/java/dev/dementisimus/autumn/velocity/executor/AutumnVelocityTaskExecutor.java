/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.executor;

import com.velocitypowered.api.proxy.ProxyServer;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import org.jetbrains.annotations.NotNull;

public record AutumnVelocityTaskExecutor(ProxyServer proxyServer) implements AutumnTaskExecutor {

    @Override
    public void synchronous(@NotNull Runnable runnable) {
        this.proxyServer.getScheduler().buildTask(this.proxyServer, runnable).schedule();
    }
}
