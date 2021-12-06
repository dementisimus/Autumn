/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.injection.module.provider;

import com.google.inject.Provider;

/**
 * Represents a generic injection module provider
 *
 * @param <T> provider class type
 *
 * @since 1.0.0
 */
public record GenericInjectionProvider<T>(T value) implements Provider<T> {

    @Override
    public T get() {
        return this.value;
    }
}
