/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dementisimus.autumn.common.CustomAutumn;
import dev.dementisimus.autumn.common.api.injection.AutumnInjector;
import dev.dementisimus.autumn.common.api.server.ServerType;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.injection.CustomAutumnInjector;
import dev.dementisimus.autumn.common.setup.CustomSetupManager;
import dev.dementisimus.autumn.common.setup.value.DefaultSetupValueManager;
import dev.dementisimus.autumn.velocity.executor.AutumnVelocityTaskExecutor;
import dev.dementisimus.autumn.velocity.injection.AutumnVelocityInjector;
import dev.dementisimus.autumn.velocity.log.AutumnVelocityLogging;
import dev.dementisimus.autumn.velocity.setup.VelocitySetupManager;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class VelocityAutumn extends CustomAutumn {

    private final ProxyServer proxyServer;
    private final PluginManager pluginManager;
    private final EventManager eventManager;
    private final CommandManager commandManager;
    private final PluginDescription pluginDescription;
    private final Path pluginDirectory;

    public VelocityAutumn(Object plugin, ProxyServer proxyServer, Logger logger, PluginDescription pluginDescription, Path pluginDirectory, String pluginPrefix) {
        super(plugin, pluginPrefix, new AutumnVelocityTaskExecutor(proxyServer), new AutumnVelocityLogging(logger));

        this.proxyServer = proxyServer;
        this.pluginManager = proxyServer.getPluginManager();
        this.eventManager = proxyServer.getEventManager();
        this.commandManager = proxyServer.getCommandManager();
        this.pluginDescription = pluginDescription;
        this.pluginDirectory = pluginDirectory;

        super.postConstructorInitialization();
        this.setAutumnClassLoader(this.getClass().getClassLoader());

        CustomSetupManager setupManager = this.getSetupManager();
        if(!setupManager.isCompleted()) {
            DefaultSetupValueManager setupValueManager = new DefaultSetupValueManager(this, setupManager);

            super.setSetupValueManager(setupValueManager);
            this.eventManager.register(this.plugin(), CommandExecuteEvent.class, event -> {
                String command = event.getCommand();

                if(setupManager.isCompleted() || command.equalsIgnoreCase("stop")) {
                    event.setResult(CommandExecuteEvent.CommandResult.allowed());
                    return;
                }

                event.setResult(CommandExecuteEvent.CommandResult.denied());
                setupValueManager.setConsoleInput(command);
            });
        }
    }

    @Override
    protected void initializePluginDetails(Object pluginObject) {
        super.setSetupManager(new VelocitySetupManager(this, this.eventManager));
        super.setPluginClassLoader(pluginObject.getClass().getClassLoader());
        super.setPluginName(this.pluginDescription.getName().orElse("?"));
        super.setPluginVersion(this.pluginDescription.getVersion().orElse("?"));
        super.setServertype(ServerType.VELOCITY);
        super.setServerVersion(this.proxyServer.getVersion().getVersion());
        super.setPluginFolder(this.pluginDirectory.toFile());
    }

    @Override
    protected void initializePlugin(Object pluginObject) {
        ProxyServer proxyServer = (ProxyServer) pluginObject;
        AutumnInjector injector = this.getInjector();

        injector.registerModule(VelocityAutumn.class, this);
        injector.registerModule(ProxyServer.class, proxyServer);
        injector.registerModule(PluginDescription.class, this.pluginDescription);
    }

    @Override
    protected void loadPlugin(File pluginFile) {
        this.logging().warning(new CustomAutumnTranslation("autumn.velocity.plugin.loading.not.supported"));
        this.proxyServer.getScheduler().buildTask(this.plugin(), this.proxyServer::shutdown).delay(5, TimeUnit.SECONDS).schedule();
    }

    @Override
    protected boolean isLoadedPlugin(String plugin) {
        return this.pluginManager.isLoaded(plugin);
    }

    @Override
    protected CustomAutumnInjector getAutumnInjector(Object pluginObject) {
        return new AutumnVelocityInjector(this, this.eventManager, this.commandManager);
    }
}
