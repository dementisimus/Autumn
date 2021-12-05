package dev.dementisimus.autumn.common.api.callback;

/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnCallback @ AutumnCommon
 *
 * @author dementisimus
 * @since 22.11.2021:22:21
 */
public interface AutumnTriCallback<A, B, C> {

    void done(A a, B b, C c);

}
