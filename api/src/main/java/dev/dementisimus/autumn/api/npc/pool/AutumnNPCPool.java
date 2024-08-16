/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.npc.pool;

import dev.dementisimus.autumn.api.callback.DoubleCallback;
import dev.dementisimus.autumn.api.i18n.PlayerTranslation;
import dev.dementisimus.autumn.api.npc.AutumnNPC;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface AutumnNPCPool {

    AutumnNPC register(AutumnNPC npc);

    AutumnNPC unregister(AutumnNPC npc);

    void onInteraction(String id, DoubleCallback<@NotNull Player, AutumnNPC.@NotNull AutumnNPCInteractionAction> interaction);

    void onTranslation(String id, DoubleCallback<@NotNull Player, @NotNull PlayerTranslation> context);

    default void registerAndShowAll(AutumnNPC npc) {
        this.register(npc).show();
    }
}
