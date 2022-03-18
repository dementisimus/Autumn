/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.velocity.setup.event;

import dev.dementisimus.autumn.common.api.setup.event.ValidateCurrentExtraSetupStateEvent;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class VelocityValidateCurrentExtraSetupStateEvent implements ValidateCurrentExtraSetupStateEvent {

    private final SetupState currentSetupState;
    private final String consoleInput;

    private boolean validInput;

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
}
