/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.setup.event;

import dev.dementisimus.autumn.api.setup.state.SetupState;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class DeserializeSetupStateEvent extends Event implements dev.dementisimus.autumn.api.setup.event.DeserializeSetupStateEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final SetupState setupState;
    private final Document configuration;
    private final String name;

    private Object value;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull SetupState setupState() {
        return this.setupState;
    }

    @Override
    public @NotNull Document configuration() {
        return this.configuration;
    }

    @Override
    public @NotNull String name() {
        return this.name;
    }

    @Override
    public @NotNull Object value() {
        return this.value;
    }

    @Override
    public void value(@NotNull Object value) {
        this.value = value;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
