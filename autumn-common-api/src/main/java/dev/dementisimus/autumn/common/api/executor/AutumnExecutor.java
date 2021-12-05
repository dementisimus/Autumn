package dev.dementisimus.autumn.common.api.executor;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnExecutor @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:23:00
 */
public interface AutumnExecutor {

    void schedule(Runnable runnable);

    void scheduleWithFixedDelay(Runnable runnable);

    void cancel();
}
