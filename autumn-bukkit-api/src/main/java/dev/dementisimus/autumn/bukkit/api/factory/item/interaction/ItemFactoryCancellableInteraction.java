/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item.interaction;

/**
 * Represents a cancellable item factory interaction
 *
 * @since 1.1.2
 */
public interface ItemFactoryCancellableInteraction {

    /**
     * Returns true if the drop interaction is cancelled, false otherwise
     *
     * @return true if the drop interaction is cancelled, false otherwise
     *
     * @since 1.1.2
     */
    boolean cancelled();

    /**
     * Sets the cancellation state of the drop interaction
     *
     * @param cancelled true if cancelled, false otherwise
     *
     * @since 1.1.2
     */
    void cancelled(boolean cancelled);
}
