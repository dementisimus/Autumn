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
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class ValidateCurrentExtraSetupStateEvent extends Event implements dev.dementisimus.autumn.api.setup.event.ValidateCurrentExtraSetupStateEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final SetupState currentSetupState;
    private final String consoleInput;

    private boolean validInput;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull SetupState currentSetupState() {
        return this.currentSetupState;
    }

    @Override
    public @NotNull String consoleInput() {
        return this.consoleInput;
    }

    @Override
    public boolean validInput() {
        return this.validInput;
    }

    @Override
    public void validInput(boolean validInput) {
        this.validInput = validInput;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
