package dev.dementisimus.autumn.common.api.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnTaskExecutor @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:18:45
 */
public interface AutumnTaskExecutor {

    ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

    default void asynchronous(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

    void synchronous(Runnable runnable);

    static void staticAsynchronous(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }
}
