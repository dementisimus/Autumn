package dev.dementisimus.autumn.bukkit.setup.event;

import dev.dementisimus.autumn.common.api.setup.event.SerializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class BukkitSerializeSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:13:36
 */
@AllArgsConstructor
public class BukkitSerializeSetupStateEvent extends Event implements SerializeSetupStateEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final SetupState setupState;

    private Object value;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public SetupState setupState() {
        return this.setupState;
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public void value(Object value) {
        this.value = value;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
