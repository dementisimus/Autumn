/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.command;

import dev.dementisimus.autumn.api.command.AutumnCommand;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class CustomAutumnCommand implements AutumnCommand {

    private final String label;
    private final String permission;
    private final List<String> aliases;

    public CustomAutumnCommand(String label, String permission, List<String> aliases) {
        this.label = label;
        this.permission = permission;
        this.aliases = aliases;
    }

    public CustomAutumnCommand(String label, String permission) {
        this(label, permission, List.of());
    }

    public CustomAutumnCommand(String label) {
        this(label, null, List.of());
    }

    public CustomAutumnCommand(String label, List<String> aliases) {
        this(label, null, aliases);
    }
}
