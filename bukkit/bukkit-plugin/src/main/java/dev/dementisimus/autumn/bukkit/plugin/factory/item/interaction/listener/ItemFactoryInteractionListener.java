/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.factory.item.interaction.listener;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteractionEntry;
import dev.dementisimus.autumn.bukkit.plugin.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.bukkit.plugin.factory.item.interaction.CustomItemFactoryInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnDoubleCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnQuadrupleCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnTripleCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AutumnListener
public class ItemFactoryInteractionListener implements Listener {

    public static final Map<String, ItemFactoryInteractionEntry> INTERACTION_ENTRIES = new HashMap<>();
    public static final Map<String, Action[]> REQUESTED_INTERACTION_ACTIONS = new HashMap<>();

    @EventHandler
    public void on(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Player player = event.getPlayer();

        if (item != null && item.getItemMeta() != null && !INTERACTION_ENTRIES.isEmpty()) {
            CustomItemFactory itemFactory = CustomItemFactory.fromItemStack(item);

            if (itemFactory == null) return;

            String itemId = itemFactory.retrieveItemId();

            if (itemId != null && !itemFactory.hasCooldown(player)) {
                ItemFactoryInteractionEntry interactionEntry = INTERACTION_ENTRIES.get(itemId);
                Action[] requestedActions = REQUESTED_INTERACTION_ACTIONS.get(itemId);

                if (interactionEntry != null) {
                    if (requestedActions != null) {
                        boolean meetsActionCriteria = false;

                        for (Action requestedAction : requestedActions) {
                            if (action.equals(requestedAction)) {
                                meetsActionCriteria = true;
                                break;
                            }
                        }

                        if (!meetsActionCriteria) return;
                    }

                    ItemFactoryInteraction interaction = new CustomItemFactoryInteraction(event, item, itemFactory);
                    Object namespaceKeyEntry = interactionEntry.preCall(player, itemFactory);

                    AutumnDoubleCallback<Player, ItemFactoryInteraction> interactionCallback = interactionEntry.interactionCallback();
                    AutumnTripleCallback<Player, ItemFactoryInteraction, Object> retrieveOnInteractCallback = interactionEntry.retrieveOnInteractCallback();
                    AutumnQuadrupleCallback<Player, ItemFactoryInteraction, ItemFactory, Object> retrieveOnInteractFactoryCallback = interactionEntry.retrieveOnInteractFactoryCallback();

                    if (interactionCallback != null) {
                        interactionCallback.done(player, interaction);
                    } else if (retrieveOnInteractCallback != null) {
                        retrieveOnInteractCallback.done(player, interaction, namespaceKeyEntry);
                    } else if (retrieveOnInteractFactoryCallback != null) {
                        retrieveOnInteractFactoryCallback.done(player, interaction, itemFactory, namespaceKeyEntry);
                    }
                }
            }
        }
    }
}
