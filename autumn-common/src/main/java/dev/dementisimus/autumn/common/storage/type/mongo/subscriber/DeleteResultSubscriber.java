/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.storage.type.mongo.subscriber;

import com.mongodb.client.result.DeleteResult;
import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public record DeleteResultSubscriber(AutumnSingleCallback<Boolean> booleanCallback) implements Subscriber<DeleteResult> {

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(DeleteResult deleteResult) {}

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.booleanCallback.done(true);
    }
}
