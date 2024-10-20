/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.executor;

import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.executor.AutumnScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CustomAutumnScheduler implements AutumnScheduler {

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(3);

    private final long initialDelay;
    private final long delay;
    private final TimeUnit timeUnit;

    private ScheduledFuture<?> scheduledFuture;

    public CustomAutumnScheduler(long delay, TimeUnit timeUnit, SingleCallback<AutumnScheduler> executorCallback) {
        this.initialDelay = -1;
        this.delay = delay;
        this.timeUnit = timeUnit;

        executorCallback.done(this);
    }

    public CustomAutumnScheduler(long initialDelay, long delay, TimeUnit timeUnit, SingleCallback<AutumnScheduler> executorCallback) {
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
