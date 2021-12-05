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
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class ItemFactory @ BukkitAutumn
 *
 * @author dementisimus
 * @since 25.11.2021:21:25
 */
public interface ItemFactory {

    ItemFactory material(Material material);

    ItemFactory itemMeta(ItemMeta itemMeta);

    ItemFactory amount(int amount);

    ItemFactory displayName(String displayName);

    ItemFactory displayName(Player player, String translationProperty, AutumnTranslationReplacement... translationReplacements);

    ItemFactory itemFlags(ItemFlag... itemFlags);

    ItemFactory removeItemFlag(ItemFlag itemFlag);

    ItemFactory clearItemFlags();

    ItemFactory allItemFlags();

    ItemFactory emptyLore();

    ItemFactory lore(String lore);

    ItemFactory lore(Player player, String translationProperty, AutumnTranslationReplacement... translationReplacements);

    ItemFactory lore(int index, String lore);

    ItemFactory lores(String... lores);

    ItemFactory removeLore(int index);

    ItemFactory removeLore(String lore);

    ItemFactory clearLore();

    ItemFactory damage(int damage);

    ItemFactory enchantment(Enchantment enchantment, int level);

    ItemFactory enchantments(Enchantment[] enchantments, int[] levels);

    ItemFactory enchantItemForAppearance();

    ItemFactory customPotionEffect(PotionEffect potionEffect, boolean overwrite);

    ItemFactory potionColor(Color potionColor);

    ItemFactory owningPlayer(UUID uuid);

    ItemFactory leatherColor(Color color);

    <T> ItemFactory store(String namespace, String key, PersistentDataType<T, T> persistentDataType, T data);

    <T> T retrieve(String namespace, String key, PersistentDataType<T, T> persistentDataType);

    void onClick(AutumnCallback<ItemFactoryClickInteraction> clickInteractionCallback);

    ItemStack create();
}
