/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents an executor used for sync/async operations
 *
 * @since 1.0.0
 */
public interface AutumnTaskExecutor {

    ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

    /**
     * Executes as a synchronous task
     *
     * @param runnable runnable
     *
     * @since 1.0.0
     */
    void synchronous(Runnable runnable);

    /**
     * Executes as a asynchronous task
     *
     * @param runnable runnable
     *
     * @since 1.0.0
     */
    default void asynchronous(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

    /**
     * [!] Executes as a asynchronous task
     * [!] <strong>This function should not be used by external plugins!</strong>
     *
     * @param runnable runnable
     *
     * @since 1.0.0
     */
    static void staticAsynchronous(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }
}
