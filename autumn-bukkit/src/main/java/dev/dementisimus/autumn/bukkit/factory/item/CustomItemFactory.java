/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.namespace.ItemFactoryNamespace;
import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.listener.ItemFactoryClickInteractionListener;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.listener.ItemFactoryInteractionListener;
import dev.dementisimus.autumn.bukkit.helper.BukkitHelper;
import dev.dementisimus.autumn.bukkit.i18n.CustomBukkitTranslation;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslationReplacement;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CustomItemFactory implements ItemFactory {

    @Getter private String itemId;

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public CustomItemFactory(ItemStack itemStack) {
        this.initialize(itemStack);
    }

    public CustomItemFactory(Material material) {
        this(new ItemStack(material));
    }

    public CustomItemFactory(String headID, AutumnCallback<ItemFactory> itemFactoryCallback) {
        BukkitHelper.playerHeadByUrl(headID, playerHead -> {
            this.initialize(playerHead);

            itemFactoryCallback.done(this);
        });
    }

    @Override
    public @NotNull ItemFactory material(@NotNull Material material) {
        this.itemStack.setType(material);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory itemMeta(@NotNull ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory amount(int amount) {
        this.itemStack.setAmount(amount);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory displayName(@NotNull String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory displayName(@NotNull Player player, @NotNull String translationProperty, @NotNull AutumnTranslationReplacement... translationReplacements) {
        AutumnBukkitTranslation translation = new CustomBukkitTranslation(translationProperty);
        translation.replacement(translationReplacements);

        return this.displayName(translation.get(player));
    }

    @Override
    public @NotNull ItemFactory itemFlags(@NotNull ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory removeItemFlag(@NotNull ItemFlag itemFlag) {
        this.itemMeta.removeItemFlags(itemFlag);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory clearItemFlags() {
        this.itemMeta.removeItemFlags(ItemFlag.values());
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory allItemFlags() {
        this.itemMeta.addItemFlags(ItemFlag.values());
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory emptyLore() {
        this.getLore().add(" ");
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory lore(@NotNull String lore) {
        this.getLore().add(lore);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory lore(@NotNull Player player, @NotNull String translationProperty, @NotNull AutumnTranslationReplacement... translationReplacements) {
        AutumnBukkitTranslation translation = new CustomBukkitTranslation(translationProperty);
        translation.replacement(translationReplacements);

        return this.lore(translation.get(player));
    }

    @Override
    public @NotNull ItemFactory lore(int index, @NotNull String lore) {
        this.getLore().set(index, lore);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory lores(@NotNull String... lores) {
        this.getLore().addAll(List.of(lores));
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory removeLore(int index) {
        this.getLore().remove(index);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory removeLore(@NotNull String lore) {
        this.getLore().remove(lore);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory clearLore() {
        this.getLore().clear();
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory damage(int damage) {
        if(this.itemMeta instanceof Damageable damageable) {
            damageable.setDamage(damage);
        }
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory enchantment(@NotNull Enchantment enchantment, int level) {
        Preconditions.checkNotNull(enchantment, "Enchantment may not be null!");

        this.itemMeta.addEnchant(enchantment, level, true);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory enchantments(@NotNull Enchantment[] enchantments, int[] levels) {
        for(int i = 0; i < enchantments.length; i++) this.enchantment(enchantments[i], levels[i]);
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory enchantItemForAppearance() {
        this.enchantment(Enchantment.DAMAGE_ALL, 1);
        return this.allItemFlags();
    }

    @Override
    public @NotNull ItemFactory customPotionEffect(@NotNull PotionEffect potionEffect, boolean overwrite) {
        if(this.itemMeta instanceof PotionMeta potionMeta) {
            potionMeta.addCustomEffect(potionEffect, overwrite);
        }
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory potionColor(@NotNull Color potionColor) {
        if(this.itemMeta instanceof PotionMeta potionMeta) {
            potionMeta.setColor(potionColor);
        }
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory owningPlayer(@NotNull UUID uuid) {
        if(this.itemMeta instanceof SkullMeta skullMeta) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        }
        return this.apply();
    }

    @Override
    public @NotNull ItemFactory leatherColor(@NotNull Color color) {
        if(this.itemMeta instanceof LeatherArmorMeta leatherArmorMeta) {
            leatherArmorMeta.setColor(color);
        }
        return this.apply();
    }

    @Override
    public @NotNull <T> ItemFactory store(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType, @NotNull T data) {
        if(this.retrieve(namespace, key, persistentDataType) == null) {
            NamespacedKey namespacedKey = this.namespacedKey(namespace, key);

            this.persistentDataContainer().set(namespacedKey, persistentDataType, data);
        }
        return this.apply();
    }

    @Override
    public <T> @Nullable T retrieve(@NotNull String namespace, @NotNull String key, @NotNull PersistentDataType<T, T> persistentDataType) {
        NamespacedKey namespacedKey = this.namespacedKey(namespace, key);

        return this.persistentDataContainer().get(namespacedKey, persistentDataType);
    }

    @Override
    public void onClick(@NotNull AutumnCallback<@NotNull ItemFactoryClickInteraction> clickInteractionCallback) {
        ItemFactoryClickInteractionListener.REQUESTED_INTERACTIONS.put(this.itemId, clickInteractionCallback);
    }

    @Override
    public void onInteract(@NotNull AutumnCallback<@NotNull ItemFactoryInteraction> interactionCallback) {
        ItemFactoryInteractionListener.REQUESTED_INTERACTIONS.put(this.itemId, interactionCallback);
    }

    @Override
    public void onInteract(@NotNull AutumnCallback<@NotNull ItemFactoryInteraction> interactionCallback, @NotNull Action... actions) {
        this.onInteract(interactionCallback);
        ItemFactoryInteractionListener.REQUESTED_INTERACTION_ACTIONS.put(this.itemId, actions);
    }

    @Override
    public @NotNull ItemStack create() {
        return this.itemStack;
    }

    private void initialize(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();

        String itemId = this.retrieve(ItemFactoryNamespace.NAMESPACE, ItemFactoryNamespace.ITEM_ID, PersistentDataType.STRING);

        this.itemId = Objects.requireNonNullElseGet(itemId, () -> RandomStringUtils.randomAlphanumeric(5));

        if(this.itemMeta != null && itemId == null) {
            this.store(ItemFactoryNamespace.NAMESPACE, ItemFactoryNamespace.ITEM_ID, PersistentDataType.STRING, this.itemId);
        }
    }

    private List<String> getLore() {
        return ((this.itemStack.getItemMeta() == null || this.itemStack.getItemMeta().getLore() == null) ? new ArrayList<>() : this.itemStack.getItemMeta().getLore());
    }

    private PersistentDataContainer persistentDataContainer() {
        return this.itemMeta.getPersistentDataContainer();
    }

    private NamespacedKey namespacedKey(String namespace, String key) {
        return new NamespacedKey(namespace.toLowerCase(), key.toLowerCase());
    }

    private ItemFactory apply() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this;
    }
}
