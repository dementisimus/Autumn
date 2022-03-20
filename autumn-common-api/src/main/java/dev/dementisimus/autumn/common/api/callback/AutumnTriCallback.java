/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.callback;

/**
 * Represents an interface for passing on data
 *
 * @param <A> class type for element a
 * @param <B> class type for element b
 * @param <C> class type for element c
 *
 * @since 1.0.0
 */
public interface AutumnTriCallback<A, B, C> {

    /**
     * @param a element a
     * @param b element b
     * @param c element c
     *
     * @since 1.0.0
     */
    void done(A a, B b, C c);

}
