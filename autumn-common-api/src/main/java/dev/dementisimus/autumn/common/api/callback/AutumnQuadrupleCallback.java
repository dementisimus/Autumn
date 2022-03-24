/*
 | Copyright 2021 dementisimus,
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
 * @param <D> class type for element d
 *
 * @since 1.1.2
 */
public interface AutumnQuadrupleCallback<A, B, C, D> {

    /**
     * @param a element a
     * @param b element b
     * @param c element c
     * @param d element d
     *
     * @since 1.1.2
     */
    void done(A a, B b, C c, D d);
}
