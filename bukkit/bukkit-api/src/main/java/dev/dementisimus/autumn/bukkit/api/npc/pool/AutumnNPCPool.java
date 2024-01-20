/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.api.npc.pool;

import dev.dementisimus.autumn.bukkit.api.i18n.AutumnBukkitTranslation;
import dev.dementisimus.autumn.bukkit.api.npc.AutumnNPC;
import dev.dementisimus.autumn.common.api.callback.AutumnDoubleCallback;
import org.bukkit.entity.Player;

public interface AutumnNPCPool {

    AutumnNPC register(AutumnNPC npc);

    AutumnNPC unregister(AutumnNPC npc);

    void onInteraction(String id, AutumnDoubleCallback<Player, AutumnNPC.AutumnNPCInteractionAction> interaction);

    void onTranslation(String id, AutumnDoubleCallback<Player, AutumnBukkitTranslation> context);

    default void registerAndShowAll(AutumnNPC npc) {
        this.register(npc).show();
    }
}
