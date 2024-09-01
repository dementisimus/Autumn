/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.storage.type.mongo.subscriber;

import com.mongodb.client.result.InsertOneResult;
import dev.dementisimus.autumn.api.callback.SingleCallback;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public record InsertSubscriber(SingleCallback<Boolean> booleanCallback) implements Subscriber<InsertOneResult> {

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    public void onNext(InsertOneResult insertOneResult) {
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.booleanCallback.done(true);
    }
}
