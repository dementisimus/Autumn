package dev.dementisimus.autumn.common.api.injection.module;

import com.google.inject.AbstractModule;
import dev.dementisimus.autumn.common.api.injection.module.provider.GenericInjectionProvider;
import lombok.Getter;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class GenericInjectionModule @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:21:06
 */
public class GenericInjectionModule<T> extends AbstractModule {

    @Getter private final Class<T> genericObject;
    private final GenericInjectionProvider<T> genericInjectionProvider;

    public GenericInjectionModule(Class<T> clazz, T value) {
        this.genericObject = clazz;
        this.genericInjectionProvider = new GenericInjectionProvider<>(value);
    }

    @Override
    protected void configure() {
        this.bind(this.genericObject).toProvider(this.genericInjectionProvider);
    }
}
