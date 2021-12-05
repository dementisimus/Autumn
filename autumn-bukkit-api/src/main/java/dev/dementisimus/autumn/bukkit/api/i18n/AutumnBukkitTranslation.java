package dev.dementisimus.autumn.bukkit.api.i18n;

import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import org.bukkit.entity.Player;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnBukkitTranslation @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:14:47
 */
public interface AutumnBukkitTranslation extends AutumnTranslation {

    String get(Player player);
}
