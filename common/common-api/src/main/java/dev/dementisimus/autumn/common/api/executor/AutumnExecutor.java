/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.executor;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an executor for scheduled tasks
 *
 * @since 1.0.0
 */
public interface AutumnExecutor {

    /**
     * Starts the schedule
     *
     * @param runnable runnable
     * @since 1.0.0
     */
    void schedule(@NotNull Runnable runnable);

    /**
     * Starts the schedule with a fixed delay
     *
     * @param runnable runnable
     * @since 1.0.0
     */
    void scheduleWithFixedDelay(@NotNull Runnable runnable);

    /**
     * Cancels the current schedule
     *
     * @since 1.0.0
     */
    void cancel();
}
