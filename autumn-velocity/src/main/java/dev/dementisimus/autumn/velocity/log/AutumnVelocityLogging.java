/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.log;

import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class AutumnVelocityLogging implements AutumnLogging {

    private final Logger logger;

    private AutumnLanguage consoleLanguage;
    private String pluginName;
    private String pluginVersion;
    private String pluginPrefix;

    public AutumnVelocityLogging(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(@NotNull String info) {
        this.logger.info(info);
    }

    @Override
    public void info(@NotNull AutumnTranslation infoTranslation) {
        this.info(this.translation(infoTranslation));
    }

    @Override
    public void warning(@NotNull String warning) {
        this.logger.warn(warning);
    }

    @Override
    public void warning(@NotNull AutumnTranslation warningTranslation) {
        this.warning(this.translation(warningTranslation));
    }

    @Override
    public void error(@NotNull String error) {
        this.logger.error(error);
    }

    @Override
    public void error(@NotNull AutumnTranslation errorTranslation) {
        this.error(this.translation(errorTranslation));
    }

    @Override
    public void consoleLanguage(AutumnLanguage consoleLanguage) {
        this.consoleLanguage = consoleLanguage;
    }

    @Override
    public void pluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    public void pluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    @Override
    public void pluginPrefix(String pluginPrefix) {
        this.pluginPrefix = pluginPrefix;
    }

    private String translation(AutumnTranslation translation) {
        translation.replacement("plugin", this.pluginName);
        translation.replacement("version", this.pluginVersion);
        translation.replacement("prefix", this.pluginPrefix);

        return translation.get(this.consoleLanguage);
    }
}
