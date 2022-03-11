/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.injection.CustomAutumnInjector;
import dev.dementisimus.autumn.velocity.executor.AutumnVelocityTaskExecutor;
import dev.dementisimus.autumn.velocity.log.AutumnVelocityLogging;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class VelocityAutumn extends CustomAutumn {

    private final ProxyServer proxyServer;
    private final Path pluginDirectory;

    public VelocityAutumn(ProxyServer proxyServer, Logger logger, Path pluginDirectory, String pluginPrefix) {
        super(proxyServer, pluginPrefix, new AutumnVelocityTaskExecutor(proxyServer), new AutumnVelocityLogging(logger));

        this.proxyServer = proxyServer;
        this.pluginDirectory = pluginDirectory;
    }

    @Override
    protected void initializePluginDetails(Object pluginObject) {

    }

    @Override
    protected void initializePlugin(Object pluginObject) {

    }

    @Override
    protected void loadPlugin(File pluginFile) {

    }

    @Override
    protected boolean isLoadedPlugin(String plugin) {
        return false;
    }

    @Override
    protected CustomAutumnInjector getAutumnInjector(Object pluginObject) {
        return null;
    }
}
