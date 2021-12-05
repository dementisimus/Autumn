package dev.dementisimus.autumn.bukkit.setup.event;

import dev.dementisimus.autumn.common.api.setup.event.ValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import lombok.AllArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class BukkitValidateCurrentExtraSetupStateEvent @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:14:19
 */
@AllArgsConstructor
public class BukkitValidateCurrentExtraSetupStateEvent extends Event implements ValidateCurrentExtraSetupStateEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final SetupState currentSetupState;
    private final String consoleInput;

    private boolean validInput;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public SetupState currentSetupState() {
        return this.currentSetupState;
    }

    @Override
    public String consoleInput() {
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
