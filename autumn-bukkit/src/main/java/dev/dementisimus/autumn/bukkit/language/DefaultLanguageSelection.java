/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.language;

import dev.dementisimus.autumn.bukkit.BukkitAutumn;
import dev.dementisimus.autumn.bukkit.api.factory.inventory.InventoryFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.language.LanguageSelection;
import dev.dementisimus.autumn.bukkit.factory.inventory.DefaultInventoryFactory;
import dev.dementisimus.autumn.bukkit.factory.item.DefaultItemFactory;
import dev.dementisimus.autumn.bukkit.helper.BukkitHelper;
import dev.dementisimus.autumn.common.api.database.Database;
import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.database.property.AutumnUpdateDataProperty;
import dev.dementisimus.autumn.common.language.PlayerLanguage;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class DefaultLanguageSelection implements LanguageSelection {

    private static final String NAMESPACE = "AUTUMN_LANGUAGE_SELECTION";
    private static final String KEY = "LANGUAGE";

    private final BukkitAutumn autumn;

    @Override
    public void open(@NotNull Player player) {
        InventoryFactory inventoryFactory = new DefaultInventoryFactory(3, player, "autumn.bukkit.player.language.selection");

        Database database = this.autumn.getDatabase();

        inventoryFactory.setPlaceholders(Material.WHITE_STAINED_GLASS_PANE);
        for(AutumnLanguage language : AutumnLanguage.values()) {
            new DefaultItemFactory(language.getTextureId(), itemFactory -> {
                itemFactory.store(NAMESPACE, KEY, PersistentDataType.STRING, language.name());

                inventoryFactory.setItem(language.getSelectionInventorySlot(), itemFactory.displayName(player, language.getTranslationProperty()));

                itemFactory.onClick(itemFactoryClickInteraction -> {
                    ItemFactory clickedItemFactory = itemFactoryClickInteraction.itemFactory();

                    AutumnLanguage newLanguage = AutumnLanguage.valueOf(clickedItemFactory.retrieve(NAMESPACE, KEY, PersistentDataType.STRING));

                    if(database != null) {
                        database.dataSourceProperty(AutumnLanguage.DataSource.PROPERTY);

                        UpdateDataProperty updateDataProperty = AutumnUpdateDataProperty.of(AutumnLanguage.DataSource.USER, player.getUniqueId().toString());
                        updateDataProperty.value(AutumnLanguage.DataSource.LANGUAGE, newLanguage.name());

                        database.document(updateDataProperty.fullDocument());
                        database.updateDataProperty(updateDataProperty);
                        database.writeOrUpdate(success -> {
                            this.apply(player, newLanguage);
                        });
                    }else {
                        this.apply(player, newLanguage);
                    }
                });
            });
        }

        inventoryFactory.createFor(player);
    }

    private void apply(Player player, AutumnLanguage newLanguage) {
        PlayerLanguage.overwrite(player.getUniqueId(), newLanguage.getLocale());

        this.autumn.getTaskExecutor().synchronous(() -> {
            BukkitHelper.playSound(player, Sound.ENTITY_VILLAGER_YES);

            player.closeInventory();
        });
    }
}
