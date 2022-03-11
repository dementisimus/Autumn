/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.server;

/**
 * All supported server types
 *
 * @since 1.0.0
 */
public enum ServerType {

    /**
     * Spigot by SpigotMC
     *
     * @since 1.0.0
     */
    SPIGOT,

    /**
     * Paper by PaperMC
     *
     * @since 1.0.0
     */
    PAPER,

    /**
     * Bungeecord by SpigotMC
     *
     * @since 1.0.0
     */
    BUNGEECORD,

    /**
     * Waterfall by PaperMC
     *
     * @since 1.0.0
     */
    WATERFALL,

    /**
     * Velocity by PaperMC
     *
     * @since 1.1.1
     */
    VELOCITY
}
