/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.log;

import dev.dementisimus.autumn.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.api.i18n.Translation;
import dev.dementisimus.autumn.api.log.AutumnLogger;
import lombok.AllArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class CustomAutumnLogger implements AutumnLogger {

    private Logger logger;
    private AutumnLanguage consoleLanguage;
    private String pluginName;
    private String pluginVersion;
    private String pluginPrefix;

    @Override
    public void info(String info) {
        this.logger.log(Level.INFO, info);
    }

    @Override
    public void info(Translation infoTranslation) {
        this.info(this.translation(infoTranslation));
    }

    @Override
    public void warning(String warning) {
        this.logger.log(Level.WARNING, warning);
    }

    @Override
    public void warning(Translation warningTranslation) {
        this.warning(this.translation(warningTranslation));
    }

    @Override
    public void error(String error) {
        this.logger.log(Level.SEVERE, error);
    }

    @Override
    public void error(Translation errorTranslation) {
        this.error(this.translation(errorTranslation));
    }

    @Override
    public void error(Throwable throwable) {
        this.logger.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    private String translation(Translation translation) {
        translation.argument("plugin", this.pluginName);
        translation.argument("version", this.pluginVersion);
        translation.argument("prefix", this.pluginPrefix);

        return translation.get(this.consoleLanguage);
    }
}
