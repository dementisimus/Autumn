/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item.interaction;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteractionEntry;
import dev.dementisimus.autumn.bukkit.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.common.api.callback.AutumnDoubleCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnQuadrupleCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnTripleCallback;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomItemFactoryInteractionEntry<T> implements ItemFactoryInteractionEntry {

    private final String namespace;
    private final String key;
    private final PersistentDataType<T, T> persistentDataType;

    private AutumnDoubleCallback<Player, ItemFactoryClickInteraction> clickInteractionCallback;
    private AutumnTripleCallback<Player, ItemFactoryClickInteraction, T> retrieveOnClickInteractionCallback;
    private AutumnQuadrupleCallback<Player, ItemFactoryClickInteraction, ItemFactory, T> retrieveOnClickInteractionFactoryCallback;

    private AutumnDoubleCallback<Player, ItemFactoryInteraction> interactionCallback;
    private AutumnTripleCallback<Player, ItemFactoryInteraction, T> retrieveOnInteractCallback;
    private AutumnQuadrupleCallback<Player, ItemFactoryInteraction, ItemFactory, T> retrieveOnInteractFactoryCallback;

    public CustomItemFactoryInteractionEntry(String namespace, String key, PersistentDataType<T, T> persistentDataType) {
        this.namespace = namespace;
        this.key = key;
        this.persistentDataType = persistentDataType;
    }

    public CustomItemFactoryInteractionEntry(AutumnDoubleCallback<Player, ItemFactoryClickInteraction> clickInteractionCallback, AutumnDoubleCallback<Player, ItemFactoryInteraction> interactionCallback) {
        this(null, null, null);

        this.clickInteractionCallback = clickInteractionCallback;
        this.interactionCallback = interactionCallback;
    }

    @Override
    public String namespace() {
        return this.namespace;
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public PersistentDataType<T, T> persistentDataType() {
        return this.persistentDataType;
    }

    @Override
    public @Nullable AutumnDoubleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction> clickInteractionCallback() {
        return this.clickInteractionCallback;
    }

    @Override
    public @Nullable AutumnTripleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction, @Nullable T> retrieveOnClickInteractionCallback() {
        return this.retrieveOnClickInteractionCallback;
    }

    @Override
    public @Nullable AutumnQuadrupleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction, @NotNull ItemFactory, @Nullable T> retrieveOnClickInteractionFactoryCallback() {
        return this.retrieveOnClickInteractionFactoryCallback;
    }

    @Override
    public @Nullable AutumnDoubleCallback<@NotNull Player, @NotNull ItemFactoryInteraction> interactionCallback() {
        return this.interactionCallback;
    }

    @Override
    public @Nullable AutumnTripleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @Nullable T> retrieveOnInteractCallback() {
        return this.retrieveOnInteractCallback;
    }

    @Override
    public @Nullable AutumnQuadrupleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @NotNull ItemFactory, @Nullable T> retrieveOnInteractFactoryCallback() {
        return this.retrieveOnInteractFactoryCallback;
    }

    @Override
    public @Nullable Object preCall(@NotNull Player player, @NotNull ItemFactory itemFactory) {
        Object namespaceKeyEntry = itemFactory.retrieve(this.namespace(), this.key(), this.persistentDataType());

        ((CustomItemFactory) itemFactory).enableCooldown(player);

        return namespaceKeyEntry;
    }

    public ItemFactoryInteractionEntry retrieveOnClickInteractionCallback(AutumnTripleCallback<Player, ItemFactoryClickInteraction, T> retrieveOnClickInteractionCallback) {
        this.retrieveOnClickInteractionCallback = retrieveOnClickInteractionCallback;

        return this;
    }

    public ItemFactoryInteractionEntry retrieveOnClickInteractionFactoryCallback(AutumnQuadrupleCallback<Player, ItemFactoryClickInteraction, ItemFactory, T> retrieveOnClickInteractionFactoryCallback) {
        this.retrieveOnClickInteractionFactoryCallback = retrieveOnClickInteractionFactoryCallback;

        return this;
    }

    public ItemFactoryInteractionEntry retrieveOnInteractCallback(AutumnTripleCallback<Player, ItemFactoryInteraction, T> retrieveOnInteractCallback) {
        this.retrieveOnInteractCallback = retrieveOnInteractCallback;

        return this;
    }

    public ItemFactoryInteractionEntry retrieveOnInteractFactoryCallback(AutumnQuadrupleCallback<Player, ItemFactoryInteraction, ItemFactory, T> retrieveOnInteractFactoryCallback) {
        this.retrieveOnInteractFactoryCallback = retrieveOnInteractFactoryCallback;

        return this;
    }
}
