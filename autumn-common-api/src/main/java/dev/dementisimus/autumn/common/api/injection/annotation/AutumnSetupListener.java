package dev.dementisimus.autumn.common.api.injection.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnSetupListener @ AutumnCommon
 *
 * @author dementisimus
 * @since 26.11.2021:21:04
 */
@Target(value = {TYPE})
@Retention(value = RUNTIME)
public @interface AutumnSetupListener {}
