package dev.dementisimus.autumn.common.executor;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.executor.AutumnExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultAutumnExecutor @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:23:02
 */
public class DefaultAutumnExecutor implements AutumnExecutor {

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(3);

    private final long initialDelay;
    private final long delay;
    private final TimeUnit timeUnit;

    private ScheduledFuture<?> scheduledFuture;

    public DefaultAutumnExecutor(long delay, TimeUnit timeUnit, AutumnCallback<AutumnExecutor> executorCallback) {
        this.initialDelay = -1;
        this.delay = delay;
        this.timeUnit = timeUnit;

        executorCallback.done(this);
    }

    public DefaultAutumnExecutor(long initialDelay, long delay, TimeUnit timeUnit, AutumnCallback<AutumnExecutor> executorCallback) {
        this.initialDelay = initialDelay;
        this.delay = delay;
        this.timeUnit = timeUnit;

        executorCallback.done(this);
    }

    @Override
    public void schedule(Runnable runnable) {
        this.scheduledFuture = SCHEDULED_EXECUTOR_SERVICE.schedule(runnable, this.delay, this.timeUnit);
    }

    @Override
    public void scheduleWithFixedDelay(Runnable runnable) {
        this.scheduledFuture = SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(runnable, this.initialDelay, this.delay, this.timeUnit);
    }

    @Override
    public void cancel() {
        this.scheduledFuture.cancel(true);
    }
}
