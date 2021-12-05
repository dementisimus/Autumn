package dev.dementisimus.autumn.bukkit.setup.event;

import com.github.derrop.documents.Document;
import dev.dementisimus.autumn.common.api.setup.event.DeserializeSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class BukkitDeserializeSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 03.12.2021:19:22
 */
@AllArgsConstructor
public class BukkitDeserializeSetupStateEvent extends Event implements DeserializeSetupStateEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final SetupState setupState;
    private final Document configuration;
    private final String name;

    private Object value;

    @Override
    public SetupState setupState() {
        return this.setupState;
    }

    @Override
    public Document configuration() {
        return this.configuration;
    }

    @Override
    public String name() {
        return this.name;
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

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
