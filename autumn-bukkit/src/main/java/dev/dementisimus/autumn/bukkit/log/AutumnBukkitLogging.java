/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.log;

import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

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
