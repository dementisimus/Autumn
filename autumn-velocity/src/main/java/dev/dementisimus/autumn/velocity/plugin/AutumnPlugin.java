/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.plugin;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dementisimus.autumn.common.debug.SysOut;
import dev.dementisimus.autumn.velocity.VelocityAutumn;
import org.slf4j.Logger;

import java.nio.file.Path;

public class AutumnPlugin {

    @Inject
    public AutumnPlugin(ProxyServer proxyServer, Logger logger, PluginDescription pluginDescription, @DataDirectory Path path) {
        VelocityAutumn velocityAutumn = new VelocityAutumn(this, proxyServer, logger, pluginDescription, path, "VELO-AUTUMN ->");

        velocityAutumn.defaultSetupStates();
        velocityAutumn.initialize(autumnInjector -> {
            SysOut.debug("INITIALIZED");
        });
    }

    @Subscribe
    public void on(ProxyInitializeEvent event) {
        SysOut.debug("-:---");
    }
}
