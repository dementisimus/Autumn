/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.factory.item;

import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.UUID;

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
    ItemFactory material(Material material);

    /**
     * Sets the itemMeta
     *
     * @param itemMeta itemMeta
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory itemMeta(ItemMeta itemMeta);

    /**
     * Sets the item amount
     *
     * @param amount amount
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory amount(int amount);

    /**
     * Sets the item display name
     *
     * @param displayName display name
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory displayName(String displayName);

    /**
     * Sets the item display name by a translation property
     *
     * @param player player
     * @param translationProperty translationProperty
     * @param translationReplacements translationReplacements
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory displayName(Player player, String translationProperty, AutumnTranslationReplacement... translationReplacements);

    /**
     * Sets item flags
     *
     * @param itemFlags itemFlags
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory itemFlags(ItemFlag... itemFlags);

    /**
     * Removes an item flag
     *
     * @param itemFlag itemFlag
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory removeItemFlag(ItemFlag itemFlag);

    /**
     * Removes all item flags
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory clearItemFlags();

    /**
     * Adds all item flags
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory allItemFlags();

    /**
     * Create an empty lore
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory emptyLore();

    /**
     * Create a lore
     *
     * @param lore lore
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory lore(String lore);

    /**
     * Create a lore by a translation property
     *
     * @param player player
     * @param translationProperty translationProperty
     * @param translationReplacements translationReplacements
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory lore(Player player, String translationProperty, AutumnTranslationReplacement... translationReplacements);

    /**
     * Sets the lore at a specific index
     *
     * @param index index
     * @param lore lore
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory lore(int index, String lore);

    /**
     * Adds lores
     *
     * @param lores lores
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory lores(String... lores);

    /**
     * Removes a lore at an specific index
     *
     * @param index index
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory removeLore(int index);

    /**
     * Removes a lore
     *
     * @param lore lore
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory removeLore(String lore);

    /**
     * Removes all lore entries
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory clearLore();

    /**
     * Sets the item damage
     *
     * @param damage damage
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory damage(int damage);

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
    ItemFactory enchantment(Enchantment enchantment, int level);

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
    ItemFactory enchantments(Enchantment[] enchantments, int[] levels);

    /**
     * Enchants the item for appearance only - {@link #allItemFlags()} will be executed to hide the enchantment
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory enchantItemForAppearance();

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
    ItemFactory customPotionEffect(PotionEffect potionEffect, boolean overwrite);

    /**
     * Sets the potion color
     *
     * @param potionColor potion color
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory potionColor(Color potionColor);

    /**
     * Sets the owning player (for skulls, etc)
     *
     * @param uuid owning player's uuid
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory owningPlayer(UUID uuid);

    /**
     * Sets the leather color
     *
     * @param color leather color
     *
     * @return the item factory object
     *
     * @since 1.0.0
     */
    ItemFactory leatherColor(Color color);

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
    <T> ItemFactory store(String namespace, String key, PersistentDataType<T, T> persistentDataType, T data);

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
    <T> T retrieve(String namespace, String key, PersistentDataType<T, T> persistentDataType);

    /**
     * Listens for clicks on this item
     *
     * @param clickInteractionCallback the Callback used to deliver the clicked interaction
     *
     * @since 1.0.0
     */
    void onClick(AutumnCallback<ItemFactoryClickInteraction> clickInteractionCallback);

    /**
     * Creates the item
     *
     * @return the item
     *
     * @since 1.0.0
     */
    ItemStack create();
}
