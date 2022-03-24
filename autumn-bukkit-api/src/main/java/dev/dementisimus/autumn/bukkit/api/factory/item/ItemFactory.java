/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item;

import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryDropInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnDoubleCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnQuadrupleCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnTripleCallback;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A factory for items
 *
 * @since 1.0.0
 */
public interface ItemFactory {

    /**
     * Sets the material
     *
     * @param material material
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory material(@NotNull Material material);

    /**
     * Sets the itemMeta
     *
     * @param itemMeta itemMeta
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory itemMeta(@NotNull ItemMeta itemMeta);

    /**
     * Sets the item amount
     *
     * @param amount amount
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory amount(int amount);

    /**
     * Sets the item display name
     *
     * @param displayName display name
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory displayName(@NotNull String displayName);

    /**
     * Sets the item display name by a translation property
     *
     * @param player player
     * @param translationProperty property
     * @param translationReplacements translationReplacements
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory displayName(@NotNull Player player, @NotNull String translationProperty, @NotNull AutumnTranslationReplacement... translationReplacements);

    /**
     * Sets item flags
     *
     * @param itemFlags itemFlags
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory itemFlags(@NotNull ItemFlag... itemFlags);

    /**
     * Removes an item flag
     *
     * @param itemFlag itemFlag
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory removeItemFlag(@NotNull ItemFlag itemFlag);

    /**
     * Removes all item flags
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory clearItemFlags();

    /**
     * Adds all item flags
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory allItemFlags();

    /**
     * Create an empty lore
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory emptyLore();

    /**
     * Create a string
     *
     * @param string string
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory lore(@NotNull String string);

    /**
     * Creates a lore with a prefix
     *
     * @param data data
     *
     * @return the item factory object
     *
     * @since 1.1.1
     */
    @NotNull ItemFactory dataLore(String data);

    /**
     * Create a lore by a translation property
     *
     * @param player player
     * @param translationProperty property
     * @param translationReplacements translationReplacements
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory lore(@NotNull Player player, @NotNull String translationProperty, @NotNull AutumnTranslationReplacement... translationReplacements);

    /**
     * Creates a lore by a translation property, with a prefix
     *
     * @param player player
     * @param translationProperty property
     * @param translationReplacements translationReplacements
     *
     * @return the item factory object
     *
     * @since 1.1.1
     */
    @NotNull ItemFactory dataLore(@NotNull Player player, @NotNull String translationProperty, @NotNull AutumnTranslationReplacement... translationReplacements);

    /**
     * Sets the string at a specific index
     *
     * @param index index
     * @param string string
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory lore(int index, @NotNull String string);

    /**
     * Adds strings
     *
     * @param strings strings
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory lores(String... strings);

    /**
     * Removes a lore at an specific index
     *
     * @param index index
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory removeLore(int index);

    /**
     * Removes a string
     *
     * @param string string
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory removeLore(@NotNull String string);

    /**
     * Removes all lore entries
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory clearLore();

    /**
     * Sets the item damage
     *
     * @param damage damage
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory damage(int damage);

    /**
     * Adds an enchantment
     *
     * @param enchantment enchantment
     * @param level level
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory enchantment(@NotNull Enchantment enchantment, int level);

    /**
     * Adds multiple enchantments
     *
     * @param enchantments enchantments
     * @param levels levels
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory enchantments(@NotNull Enchantment[] enchantments, int[] levels);

    /**
     * Enchants the item for appearance only - {@link #allItemFlags()} will be executed to hide the enchantment
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory enchantItemForAppearance();

    /**
     * Adds a custom potion effect
     *
     * @param potionEffect potionEffect
     * @param overwrite overwrite potion effects
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory customPotionEffect(@NotNull PotionEffect potionEffect, boolean overwrite);

    /**
     * Sets the potion color
     *
     * @param potionColor potion color
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory potionColor(@NotNull Color potionColor);

    /**
     * Sets the owning player (for skulls, etc)
     *
     * @param uuid owning player's uuid
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory owningPlayer(@NotNull UUID uuid);

    /**
     * Sets the leather color
     *
     * @param color leather color
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory leatherColor(@NotNull Color color);

    /**
     * Stores a PersistentDataType in this item
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     * @param data data
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull <T> ItemFactory store(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull T data);

    /**
     * Retrieves stored data
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     *
     * @return the stored data, or null, if empty
     *
     * @since 1.0.0
     */
    <T> @Nullable T retrieve(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType);

    /**
     * material
     * Sets a cooldown for the current
     *
     * @param time time
     * @param timeUnit timeUnit
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull ItemFactory cooldown(int time, @NotNull TimeUnit timeUnit);

    /**
     * Listens for clicks on this item
     *
     * @param clickInteractionCallback the Callback used to deliver the click interaction
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    @NotNull ItemFactory onClick(@NotNull AutumnDoubleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction> clickInteractionCallback);

    /**
     * Retrieves stored data and listens for clicks on this item
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     * @param retrieveOnClickInteractionCallback the Callback used to deliver the click interaction
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull <T> ItemFactory retrieveOnClick(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull AutumnTripleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction, @Nullable T> retrieveOnClickInteractionCallback);

    /**
     * Retrieves stored data and listens for clicks on this item
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     * @param retrieveOnClickInteractionFactoryCallback the Callback used to deliver the click interaction
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull <T> ItemFactory retrieveOnClick(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull AutumnQuadrupleCallback<@NotNull Player, @NotNull ItemFactoryClickInteraction, @NotNull ItemFactory, @Nullable T> retrieveOnClickInteractionFactoryCallback);

    /**
     * Listens for an interaction with this item
     *
     * @param interactionCallback the Callback used to deliver the interaction
     *
     * @return the item factory object
     *
     * @since 1.1.0
     */
    @NotNull ItemFactory onInteract(@NotNull AutumnDoubleCallback<@NotNull Player, @NotNull ItemFactoryInteraction> interactionCallback);

    /**
     * Retrieves stored data and listens for interactions with this item
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     * @param retrieveOnInteractCallback the Callback used to deliver the interaction
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull <T> ItemFactory retrieveOnInteract(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull AutumnTripleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @Nullable T> retrieveOnInteractCallback);

    /**
     * Retrieves stored data and listens for interactions with this item
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     * @param retrieveOnInteractFactoryCallback the Callback used to deliver the interaction
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull <T> ItemFactory retrieveOnInteract(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull AutumnQuadrupleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @NotNull ItemFactory, @Nullable T> retrieveOnInteractFactoryCallback);

    /**
     * Listens for an interaction with this item, triggered by specific {@link Action}s
     *
     * @param interactionCallback the Callback used to deliver the interaction
     * @param actions actions necessary for an interaction
     *
     * @return the item factory object
     *
     * @since 1.1.0
     */
    @NotNull ItemFactory onInteract(@NotNull AutumnDoubleCallback<@NotNull Player, @NotNull ItemFactoryInteraction> interactionCallback, @NotNull Action... actions);

    /**
     * Retrieves stored data and listens for interactions with this item, triggered by specific {@link Action}s
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     * @param retrieveOnInteractCallback the Callback used to deliver the interaction
     * @param actions actions necessary for an interaction
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull <T> ItemFactory retrieveOnInteract(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull AutumnTripleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @Nullable T> retrieveOnInteractCallback, @NotNull Action... actions);

    /**
     * Retrieves stored data and listens for interactions with this item, triggered by specific {@link Action}s
     *
     * @param namespace namespace
     * @param key key
     * @param persistentDataType persistentDataType
     * @param retrieveOnInteractFactoryCallback the Callback used to deliver the interaction
     * @param actions actions necessary for an interaction
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull <T> ItemFactory retrieveOnInteract(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull AutumnQuadrupleCallback<@NotNull Player, @NotNull ItemFactoryInteraction, @NotNull ItemFactory, @Nullable T> retrieveOnInteractFactoryCallback, @NotNull Action... actions);

    /**
     * Listens for a drop interaction with this item
     *
     * @param interactionCallback the Callback used to deliver the interaction
     *
     * @return the item factory object
     *
     * @since 1.1.2
     */
    @NotNull ItemFactory onDrop(@NotNull AutumnDoubleCallback<@NotNull Player, @NotNull ItemFactoryDropInteraction> interactionCallback);

    /**
     * Creates the item
     *
     * @return the item
     *
     * @since 1.0.0
     */
    @NotNull ItemStack create();
}
