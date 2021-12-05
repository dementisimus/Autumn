package dev.dementisimus.autumn.bungee.api.i18n;

import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import net.md_5.bungee.api.connection.ProxiedPlayer;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnBungeeTranslation @ Autumn
 *
 * @author dementisimus
 * @since 05.12.2021:21:47
 */
public interface AutumnBungeeTranslation extends AutumnTranslation {

    String get(ProxiedPlayer player);
}
