/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.injection.annotation;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Represents a command, executable by console or user
 *
 * @since 1.0.0
 */
@Target(value = {TYPE})
@Retention(value = RUNTIME)
public @interface AutumnCommand {

    /**
     * If the command is optional, it will only be registered if Autumn#optionalCommands(true) is used
     *
     * @return if the command is optional
     *
     * @since 1.0.0
     */
    boolean isOptional() default false;

    /**
     * The command name
     *
     * @return the command name
     *
     * @since 1.0.0
     */
    @NotNull String name();

    /**
     * The command aliases
     *
     * @return the command aliases
     *
     * @since 1.0.0
     */
    @NotNull String[] nameAliases() default {};
}
