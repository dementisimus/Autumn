package dev.dementisimus.autumn.common.api.injection.module.provider;

import com.google.inject.Provider;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class GenericInjectionProvider @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:21:08
 */
public record GenericInjectionProvider<T>(T value) implements Provider<T> {

    @Override
    public T get() {
        return this.value;
    }
}
