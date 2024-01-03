/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.injection.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Represents a listener
 *
 * @since 1.0.0
 */
@Target(value = {TYPE})
@Retention(value = RUNTIME)
public @interface AutumnListener {

    /**
     * If the listener is optional, it will only be registered if Autumn#optionalListeners(true) is used
     *
     * @return if the listener is optional
     * @since 1.0.0
     */
    boolean isOptional() default false;
}
