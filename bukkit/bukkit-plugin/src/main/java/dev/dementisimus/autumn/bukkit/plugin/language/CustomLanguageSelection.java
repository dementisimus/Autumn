/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.language;

import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.language.LanguageSelection;
import dev.dementisimus.autumn.bukkit.plugin.BukkitAutumn;
import dev.dementisimus.autumn.bukkit.plugin.factory.inventory.CustomInventoryFactory;
import dev.dementisimus.autumn.bukkit.plugin.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.bukkit.plugin.helper.BukkitHelper;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.common.plugin.language.PlayerLanguage;
import dev.dementisimus.autumn.common.plugin.storage.property.AutumnStorageUpdateProperty;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CustomLanguageSelection implements LanguageSelection {

    private static final String NAMESPACE = "AUTUMN_LANGUAGE_SELECTION";
    private static final String KEY = "LANGUAGE";

    private final BukkitAutumn autumn;

    @Override
    public void open(@NotNull Player player) {
        InventoryFactory inventoryFactory = new CustomInventoryFactory(3, player, "autumn.bukkit.player.language.selection");

        Storage storage = this.autumn.storage();

        inventoryFactory.placeholder(Material.WHITE_STAINED_GLASS_PANE);
        for (AutumnLanguage language : AutumnLanguage.values()) {
            new CustomItemFactory(language.getTextureId(), itemFactory -> {
                itemFactory.store(NAMESPACE, KEY, PersistentDataType.STRING, language.name());

                inventoryFactory.item(language.getSelectionInventorySlot(), itemFactory.displayName(player, language.getTranslationProperty()));

                itemFactory.retrieveOnClick(NAMESPACE, KEY, PersistentDataType.STRING, (interactionPlayer, itemFactoryClickInteraction, newLanguageString) -> {
                    AutumnLanguage newLanguage = AutumnLanguage.valueOf(newLanguageString);

                    if (storage != null) {
                        storage.sourceProperty(AutumnLanguage.StorageSource.PROPERTY);

                        StorageUpdateProperty storageUpdateProperty = AutumnStorageUpdateProperty.of(AutumnLanguage.StorageSource.USER, player.getUniqueId().toString());
                        storageUpdateProperty.value(AutumnLanguage.StorageSource.LANGUAGE, newLanguage.name());

                        storage.document(storageUpdateProperty.fullDocument());
                        storage.updateProperty(storageUpdateProperty);
                        storage.writeOrUpdate(success -> {
                            this.apply(player, newLanguage);
                        });
                    } else {
                        this.apply(player, newLanguage);
                    }
                });
            });
        }

        inventoryFactory.createFor(this.autumn.taskExecutor(), player);
    }

    private void apply(Player player, AutumnLanguage newLanguage) {
        PlayerLanguage.overwrite(player.getUniqueId(), newLanguage.getLocale());

        this.autumn.taskExecutor().synchronous(() -> {
            BukkitHelper.playSound(player, Sound.ENTITY_VILLAGER_YES);

            player.closeInventory();
        });
    }
}
