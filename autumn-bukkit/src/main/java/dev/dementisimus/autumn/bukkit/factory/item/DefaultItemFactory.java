package dev.dementisimus.autumn.bukkit.factory.item;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryClickInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.namespace.ItemFactoryNamespace;
import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.listener.ItemFactoryClickInteractionListener;
import dev.dementisimus.autumn.bukkit.helper.BukkitHelper;
import dev.dementisimus.autumn.bukkit.i18n.DefaultAutumnBukkitTranslation;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultItemFactory @ BukkitAutumn
 *
 * @author dementisimus
 * @since 25.11.2021:21:19
 */
public class DefaultItemFactory implements ItemFactory {

    @Getter private String itemId;

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public DefaultItemFactory(ItemStack itemStack) {
        this.initialize(itemStack);
    }

    public DefaultItemFactory(Material material) {
        this(new ItemStack(material));
    }

    public DefaultItemFactory(String headID, AutumnCallback<ItemFactory> itemFactoryCallback) {
        BukkitHelper.playerHeadByUrl(headID, playerHead -> {
            this.initialize(playerHead);

            itemFactoryCallback.done(this);
        });
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

    @Override
    public ItemFactory material(Material material) {
        this.itemStack.setType(material);
        return this.apply();
    }

    @Override
    public ItemFactory itemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
        return this.apply();
    }

    @Override
    public ItemFactory amount(int amount) {
        this.itemStack.setAmount(amount);
        return this.apply();
    }

    @Override
    public ItemFactory displayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this.apply();
    }

    @Override
    public ItemFactory displayName(Player player, String translationProperty, AutumnTranslationReplacement... translationReplacements) {
        AutumnBukkitTranslation translation = new DefaultAutumnBukkitTranslation(translationProperty);
        translation.replacement(translationReplacements);

        return this.displayName(translation.get(player));
    }

    @Override
    public ItemFactory itemFlags(ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        return this.apply();
    }

    @Override
    public ItemFactory removeItemFlag(ItemFlag itemFlag) {
        this.itemMeta.removeItemFlags(itemFlag);
        return this.apply();
    }

    @Override
    public ItemFactory clearItemFlags() {
        this.itemMeta.removeItemFlags(ItemFlag.values());
        return this.apply();
    }

    @Override
    public ItemFactory allItemFlags() {
        this.itemMeta.addItemFlags(ItemFlag.values());
        return this.apply();
    }

    @Override
    public ItemFactory emptyLore() {
        this.getLore().add(" ");
        return this.apply();
    }

    @Override
    public ItemFactory lore(String lore) {
        this.getLore().add(lore);
        return this.apply();
    }

    @Override
    public ItemFactory lore(Player player, String translationProperty, AutumnTranslationReplacement... translationReplacements) {
        AutumnBukkitTranslation translation = new DefaultAutumnBukkitTranslation(translationProperty);
        translation.replacement(translationReplacements);

        return this.lore(translation.get(player));
    }

    @Override
    public ItemFactory lore(int index, String lore) {
        this.getLore().set(index, lore);
        return this.apply();
    }

    @Override
    public ItemFactory lores(String... lores) {
        this.getLore().addAll(List.of(lores));
        return this.apply();
    }

    @Override
    public ItemFactory removeLore(int index) {
        this.getLore().remove(index);
        return this.apply();
    }

    @Override
    public ItemFactory removeLore(String lore) {
        this.getLore().remove(lore);
        return this.apply();
    }

    @Override
    public ItemFactory clearLore() {
        this.getLore().clear();
        return this.apply();
    }

    @Override
    public ItemFactory damage(int damage) {
        if(this.itemMeta instanceof Damageable damageable) {
            damageable.setDamage(damage);
        }
        return this.apply();
    }

    @Override
    public ItemFactory enchantment(Enchantment enchantment, int level) {
        Preconditions.checkNotNull(enchantment, "Enchantment may not be null!");

        this.itemMeta.addEnchant(enchantment, level, true);
        return this.apply();
    }

    @Override
    public ItemFactory enchantments(Enchantment[] enchantments, int[] levels) {
        for(int i = 0; i < enchantments.length; i++) this.enchantment(enchantments[i], levels[i]);
        return this.apply();
    }

    @Override
    public ItemFactory enchantItemForAppearance() {
        this.enchantment(Enchantment.DAMAGE_ALL, 1);
        return this.allItemFlags();
    }

    @Override
    public ItemFactory customPotionEffect(PotionEffect potionEffect, boolean overwrite) {
        if(this.itemMeta instanceof PotionMeta potionMeta) {
            potionMeta.addCustomEffect(potionEffect, overwrite);
        }
        return this.apply();
    }

    @Override
    public ItemFactory potionColor(Color potionColor) {
        if(this.itemMeta instanceof PotionMeta potionMeta) {
            potionMeta.setColor(potionColor);
        }
        return this.apply();
    }

    @Override
    public ItemFactory owningPlayer(UUID uuid) {
        if(this.itemMeta instanceof SkullMeta skullMeta) {
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        }
        return this.apply();
    }

    @Override
    public ItemFactory leatherColor(Color color) {
        if(this.itemMeta instanceof LeatherArmorMeta leatherArmorMeta) {
            leatherArmorMeta.setColor(color);
        }
        return this.apply();
    }

    @Override
    public <T> ItemFactory store(String namespace, String key, PersistentDataType<T, T> persistentDataType, T data) {
        if(this.retrieve(namespace, key, persistentDataType) == null) {
            NamespacedKey namespacedKey = this.namespacedKey(namespace, key);

            this.persistentDataContainer().set(namespacedKey, persistentDataType, data);
        }
        return this.apply();
    }

    @Override
    public <T> T retrieve(String namespace, String key, PersistentDataType<T, T> persistentDataType) {
        NamespacedKey namespacedKey = this.namespacedKey(namespace, key);

        return this.persistentDataContainer().get(namespacedKey, persistentDataType);
    }

    @Override
    public void onClick(AutumnCallback<ItemFactoryClickInteraction> clickInteractionCallback) {
        ItemFactoryClickInteractionListener.REQUESTED_INTERACTIONS.put(this.itemId, clickInteractionCallback);
    }

    @Override
    public ItemStack create() {
        return this.itemStack;
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
