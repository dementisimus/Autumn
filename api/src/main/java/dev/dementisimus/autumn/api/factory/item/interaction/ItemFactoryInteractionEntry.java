/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.factory.item.interaction;

import dev.dementisimus.autumn.api.callback.DoubleCallback;
import dev.dementisimus.autumn.api.callback.QuadrupleCallback;
import dev.dementisimus.autumn.api.callback.TripleCallback;
import dev.dementisimus.autumn.api.factory.item.ItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an item factory interaction entry
 *
 * @since 1.2.0
 */
public interface ItemFactoryInteractionEntry {

    /**
     * Returns the namespace
     *
     * @return the namespace
     * @since 1.2.0
     */
    String namespace();

    /**
     * Returns the key
     *
     * @return the key
     * @since 1.2.0
     */
    String key();

    /**
     * Returns the {@link PersistentDataType}
     *
     * @return the {@link PersistentDataType}
     * @since 1.2.0
     */
    <T> PersistentDataType<T, T> persistentDataType();

    /**
     * Returns the callback for the interaction
     *
     * @return the callback for the interaction
     * @since 1.2.0
     */
    @Nullable DoubleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction> clickInteractionCallback();

    /**
     * Returns the callback for the interaction
     *
     * @return the callback for the interaction
     * @since 1.2.0
     */
    @Nullable <T> TripleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction, @Nullable T> retrieveOnClickInteractionCallback();

    /**
     * Returns the callback for the interaction
     *
     * @return the callback for the interaction
     * @since 1.2.0
     */
    @Nullable <T> QuadrupleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction, @NotNull ItemFactory, @Nullable T> retrieveOnClickInteractionFactoryCallback();

    /**
     * Returns the callback for the interaction
     *
     * @return the callback for the interaction
     * @since 1.2.0
     */
    @Nullable DoubleCallback<@NotNull Player, @NotNull ItemFactoryInteraction> interactionCallback();

    /**
     * Returns the callback for the interaction
     *
     * @return the callback for the interaction
     * @since 1.2.0
     */
    @Nullable <T> TripleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @Nullable T> retrieveOnInteractCallback();

    /**
     * Returns the callback for the interaction
     *
     * @return the callback for the interaction
     * @since 1.2.0
     */
    @Nullable <T> QuadrupleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @NotNull ItemFactory, @Nullable T> retrieveOnInteractFactoryCallback();

    /**
     * Returns fetchable data, if given, otherwise null and enables the cooldown, if given
     *
     * @param player      player
     * @param itemFactory item factory
     * @return fetchable data, if given, otherwise null
     * @since 1.2.0
     */
    @Nullable Object preCall(@NotNull Player player, @NotNull ItemFactory itemFactory);
}
