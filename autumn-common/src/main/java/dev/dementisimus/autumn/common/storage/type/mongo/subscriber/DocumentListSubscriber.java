/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.storage.type.mongo.subscriber;

import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

public record DocumentListSubscriber(List<Document> documents, AutumnSingleCallback<List<Document>> listCallback) implements Subscriber<Document> {

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Document document) {
        document.remove("_id");
        this.documents.add(document);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.listCallback.done(this.documents);
    }
}
