/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.factory.item.interaction.listener;

import dev.dementisimus.autumn.bukkit.api.factory.item.ItemFactory;
import dev.dementisimus.autumn.bukkit.api.factory.item.interaction.ItemFactoryInteraction;
import dev.dementisimus.autumn.bukkit.api.factory.item.namespace.ItemFactoryNamespace;
import dev.dementisimus.autumn.bukkit.factory.item.CustomItemFactory;
import dev.dementisimus.autumn.bukkit.factory.item.interaction.CustomItemFactoryInteraction;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

@AutumnListener
public class ItemFactoryInteractionListener implements Listener {

    public static final Map<String, AutumnCallback<ItemFactoryInteraction>> REQUESTED_INTERACTIONS = new HashMap<>();
    public static final Map<String, Action[]> REQUESTED_INTERACTION_ACTIONS = new HashMap<>();

    @EventHandler
    public void on(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if(item != null && item.getItemMeta() != null) {
            ItemFactory itemFactory = new CustomItemFactory(item);
            String itemId = itemFactory.retrieve(ItemFactoryNamespace.NAMESPACE, ItemFactoryNamespace.ITEM_ID, PersistentDataType.STRING);

            if(itemId != null) {
                AutumnCallback<ItemFactoryInteraction> interactionCallback = REQUESTED_INTERACTIONS.get(itemId);
                Action[] requestedActions = REQUESTED_INTERACTION_ACTIONS.get(itemId);

                if(interactionCallback != null) {
                    if(requestedActions != null) {
                        boolean meetsActionCriteria = false;

                        for(Action requestedAction : requestedActions) {
                            if(action.equals(requestedAction)) {
                                meetsActionCriteria = true;
                                break;
                            }
                        }

                        if(!meetsActionCriteria) return;
                    }

                    interactionCallback.done(new CustomItemFactoryInteraction(event, item, itemFactory));
                }
            }
        }
    }
}
