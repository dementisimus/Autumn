/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.language;

import dev.dementisimus.autumn.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.api.language.LanguageSelection;
import dev.dementisimus.autumn.api.storage.Storage;
import dev.dementisimus.autumn.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.factory.inventory.CustomInventoryFactory;
import dev.dementisimus.autumn.plugin.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.plugin.helper.PluginHelper;
import dev.dementisimus.autumn.plugin.storage.property.CustomStorageUpdateProperty;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

@RequiredArgsConstructor
public class CustomLanguageSelection implements LanguageSelection {

    private static final String NAMESPACE = "AUTUMN_LANGUAGE_SELECTION";
    private static final String KEY = "LANGUAGE";

    private final CustomAutumn autumn;

    @Override
    public void open(Player player) {
        InventoryFactory inventoryFactory = new CustomInventoryFactory(3, player, "autumn.paper.player.language.selection");

        Storage storage = this.autumn.getStorage();

        inventoryFactory.placeholder(Material.WHITE_STAINED_GLASS_PANE);
        for (AutumnLanguage language : AutumnLanguage.values()) {
            new CustomItemFactory(language.getTextureId(), itemFactory -> {
                itemFactory.store(NAMESPACE, KEY, PersistentDataType.STRING, language.name());

                inventoryFactory.item(language.getSelectionInventorySlot(), itemFactory.displayName(player, language.getTranslationProperty()));

                itemFactory.retrieveOnClick(NAMESPACE, KEY, PersistentDataType.STRING, (interactionPlayer, itemFactoryClickInteraction, newLanguageString) -> {
                    AutumnLanguage newLanguage = AutumnLanguage.valueOf(newLanguageString);

                    if (storage != null) {
                        storage.sourceProperty(AutumnLanguage.StorageSource.PROPERTY);

                        StorageUpdateProperty storageUpdateProperty = CustomStorageUpdateProperty.of(AutumnLanguage.StorageSource.USER, player.getUniqueId().toString());
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

        inventoryFactory.createFor(this.autumn.getTaskExecutor(), player);
    }

    private void apply(Player player, AutumnLanguage newLanguage) {
        PlayerLanguage.overwrite(player.getUniqueId(), newLanguage.getLocale());

        this.autumn.getTaskExecutor().synchronous(() -> {
            PluginHelper.playSound(player, Sound.ENTITY_VILLAGER_YES);

            player.closeInventory();
        });
    }
}
