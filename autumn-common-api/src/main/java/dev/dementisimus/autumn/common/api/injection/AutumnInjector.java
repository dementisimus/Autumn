/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.injection;

import java.lang.annotation.Annotation;

/**
 * The injector used for all autumn injection operations
 *
 * @since 1.0.0
 */
public interface AutumnInjector {

    /**
     * Adds classloaders to the injector
     *
     * @param classLoaders class loaders to be used by the injector
     *
     * @return an {@link AutumnInjector} instance
     *
     * @since 1.0.0
     */
    AutumnInjector classLoaders(ClassLoader... classLoaders);

    /**
     * Adds annotations to the injector
     *
     * @param annotations annotations to be searched for by the injector
     *
     * @return an {@link AutumnInjector} instance
     *
     * @since 1.0.0
     */
    AutumnInjector annotation(Class<? extends Annotation>... annotations);

    /**
     * Injects the provided class loaders with the registered modules
     *
     * @since 1.0.0
     */
    void scan();

    /**
     * Generates a generic injection module
     *
     * @param clazz module class type
     * @param value module value
     * @param <T> module class type
     *
     * @since 1.0.0
     */
    <T> void registerModule(Class<T> clazz, T value);
}
