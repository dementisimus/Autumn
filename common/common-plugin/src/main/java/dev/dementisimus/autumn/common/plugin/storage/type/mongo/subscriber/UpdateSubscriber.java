/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.storage.type.mongo.subscriber;

import com.mongodb.client.result.UpdateResult;
import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public record UpdateSubscriber(AutumnSingleCallback<Boolean> booleanCallback) implements Subscriber<UpdateResult> {

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(UpdateResult updateResult) {
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