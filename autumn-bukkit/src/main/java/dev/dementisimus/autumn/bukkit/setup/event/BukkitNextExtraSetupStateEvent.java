package dev.dementisimus.autumn.bukkit.setup.event;

import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.event.NextExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class BukkitNextExtraSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 03.12.2021:20:07
 */
@AllArgsConstructor
public class BukkitNextExtraSetupStateEvent extends Event implements NextExtraSetupStateEvent {

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
    public SetupManager setupManager() {
        return this.setupManager;
    }

    @Override
    public SetupState currentSetupState() {
        return this.currentSetupState;
    }

    @Override
    public int currentStateListIndex() {
        return this.currentStateListIndex;
    }

    @Override
    public SetupState nextSetupState() {
        return this.nextSetupState;
    }

    @Override
    public void nextSetupState(SetupState nextSetupState) {
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
