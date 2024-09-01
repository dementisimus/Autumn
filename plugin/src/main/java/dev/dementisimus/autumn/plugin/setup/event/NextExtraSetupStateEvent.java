/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.setup.event;

import dev.dementisimus.autumn.api.setup.SetupManager;
import dev.dementisimus.autumn.api.setup.state.SetupState;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class NextExtraSetupStateEvent extends Event implements dev.dementisimus.autumn.api.setup.event.NextExtraSetupStateEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final SetupManager setupManager;
    private final SetupState currentSetupState;
    private final int currentStateListIndex;

    private SetupState nextSetupState;
    private boolean cancelled;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull SetupManager setupManager() {
        return this.setupManager;
    }

    @Override
    public @NotNull SetupState currentSetupState() {
        return this.currentSetupState;
    }

    @Override
    public int currentStateListIndex() {
        return this.currentStateListIndex;
    }

    @Override
    public @NotNull SetupState nextSetupState() {
        return this.nextSetupState;
    }

    @Override
    public void nextSetupState(@NotNull SetupState nextSetupState) {
        this.nextSetupState = nextSetupState;
    }

    @Override
    public boolean cancelled() {
        return this.cancelled;
    }

    @Override
    public void cancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
