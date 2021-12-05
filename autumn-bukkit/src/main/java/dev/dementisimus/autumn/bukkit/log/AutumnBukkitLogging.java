package dev.dementisimus.autumn.bukkit.log;

import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnBukkitLogging @ BukkitAutumn
 *
 * @author dementisimus
 * @since 23.11.2021:19:02
 */
public class AutumnBukkitLogging implements AutumnLogging {

    private final Logger logger = Bukkit.getLogger();

    @Override
    public void info(String info) {
        this.logger.log(Level.INFO, info);
    }

    @Override
    public void warning(String warning) {
        this.logger.log(Level.WARNING, warning);
    }

    @Override
    public void error(String error) {
        this.logger.log(Level.SEVERE, error);
    }
}
