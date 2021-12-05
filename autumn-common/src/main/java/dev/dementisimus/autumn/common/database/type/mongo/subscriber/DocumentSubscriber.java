package dev.dementisimus.autumn.common.database.type.mongo.subscriber;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DocumentSubscriber @ CoreAPI
 *
 * @author dementisimus
 * @since 07.09.2021:17:12
 */
public class DocumentSubscriber implements Subscriber<Document> {

    private final AutumnCallback<Document> documentCallback;

    private Document document;

    public DocumentSubscriber(AutumnCallback<Document> documentCallback) {
        this.documentCallback = documentCallback;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Document document) {
        document.remove("_id");
        this.document = document;
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.documentCallback.done(this.document);
    }
}
