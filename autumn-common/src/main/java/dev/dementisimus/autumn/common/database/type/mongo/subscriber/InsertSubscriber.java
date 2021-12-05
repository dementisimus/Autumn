package dev.dementisimus.autumn.common.database.type.mongo.subscriber;

import com.mongodb.client.result.InsertOneResult;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class InsertSubscriber @ CoreAPI
 *
 * @author dementisimus
 * @since 07.09.2021:17:13
 */
public record InsertSubscriber(AutumnCallback<Boolean> booleanCallback) implements Subscriber<InsertOneResult> {

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    public void onNext(InsertOneResult insertOneResult) {}

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.booleanCallback.done(true);
    }
}
