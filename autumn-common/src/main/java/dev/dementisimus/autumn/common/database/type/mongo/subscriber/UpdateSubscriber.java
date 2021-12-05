package dev.dementisimus.autumn.common.database.type.mongo.subscriber;

import com.mongodb.client.result.UpdateResult;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class UpdateSubscriber @ CoreAPI
 *
 * @author dementisimus
 * @since 07.09.2021:17:13
 */
public record UpdateSubscriber(AutumnCallback<Boolean> booleanCallback) implements Subscriber<UpdateResult> {

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(UpdateResult updateResult) {}

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.booleanCallback.done(true);
    }
}