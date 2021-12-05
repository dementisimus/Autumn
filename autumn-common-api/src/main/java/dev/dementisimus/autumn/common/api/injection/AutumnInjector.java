package dev.dementisimus.autumn.common.api.injection;

import java.lang.annotation.Annotation;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnInjector @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:21:12
 */
public interface AutumnInjector {

    AutumnInjector classLoaders(ClassLoader... classLoaders);

    AutumnInjector annotation(Class<? extends Annotation>... annotations);

    void scan();

    <T> void registerModule(Class<T> clazz, T value);
}
