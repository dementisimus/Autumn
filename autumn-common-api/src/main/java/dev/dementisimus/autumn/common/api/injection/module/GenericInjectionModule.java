/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.injection.module;

import com.google.inject.AbstractModule;
import dev.dementisimus.autumn.common.api.injection.module.provider.GenericInjectionProvider;
import lombok.Getter;

/**
 * Represents a generic injection module
 *
 * @param <T> class type
 *
 * @since 1.0.0
 */
public class GenericInjectionModule<T> extends AbstractModule {

    @Getter private final Class<T> genericObject;
    private final GenericInjectionProvider<T> genericInjectionProvider;

    /**
     * Generates a generic injection module
     *
     * @param clazz module class type
     * @param value module value
     *
     * @since 1.0.0
     */
    public GenericInjectionModule(Class<T> clazz, T value) {
        this.genericObject = clazz;
        this.genericInjectionProvider = new GenericInjectionProvider<>(value);
    }

    @Override
    protected void configure() {
        this.bind(this.genericObject).toProvider(this.genericInjectionProvider);
    }
}
