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
 * @param <T> class type for element t
 *
 * @since 1.0.0
 */
public interface AutumnCallback<T> {

    /**
     * @param t element t
     *
     * @since 1.0.0
     */
    void done(T t);

}
