/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.storage.type.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.storage.property.StorageProperty;
import dev.dementisimus.autumn.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.api.storage.type.StorageType;
import dev.dementisimus.autumn.plugin.setup.state.MainSetupStates;
import dev.dementisimus.autumn.plugin.storage.type.mongo.subscriber.*;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MongoStorage implements StorageType {

    private final com.mongodb.reactivestreams.client.MongoDatabase mongoDatabase;
    private final MongoClient mongoClient;

    public MongoStorage() {
        String uri = MainSetupStates.MONGODB_URI.asString();
        String databaseName = MainSetupStates.DATABASE.asString();

        this.mongoClient = MongoClients.create(uri);
        this.mongoDatabase = this.mongoClient.getDatabase(databaseName);
    }

    @Override
    public void read(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull SingleCallback<@Nullable Document> documentCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(storageSourceProperty);

        mongoCollection.find(storageProperty.filter()).first().subscribe(new DocumentSubscriber(documentCallback));
    }

    @Override
    public void list(@NotNull StorageSourceProperty storageSourceProperty, @NotNull SingleCallback<@NotNull List<Document>> listDocumentCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(storageSourceProperty);

        mongoCollection.find().subscribe(new DocumentListSubscriber(new ArrayList<>(), listDocumentCallback));
    }

    @Override
    public void write(@NotNull StorageSourceProperty storageSourceProperty, @NotNull Document document, @NotNull SingleCallback<@NotNull Boolean> booleanCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(storageSourceProperty);

        mongoCollection.insertOne(document).subscribe(new InsertSubscriber(booleanCallback));
    }

    @Override
    public void update(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageUpdateProperty storageUpdateProperty, @NotNull SingleCallback<@NotNull Boolean> booleanCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(storageSourceProperty);

        mongoCollection.updateOne(storageUpdateProperty.filter(), storageUpdateProperty.document()).subscribe(new UpdateSubscriber(booleanCallback));
    }

    @Override
    public void delete(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull SingleCallback<@NotNull Boolean> booleanCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(storageSourceProperty);

        mongoCollection.deleteOne(storageProperty.filter()).subscribe(new DeleteResultSubscriber(booleanCallback));
    }

    @Override
    public void isPresent(@NotNull StorageSourceProperty storageSourceProperty, @NotNull StorageProperty storageProperty, @NotNull SingleCallback<@NotNull Boolean> booleanCallback) {
        this.read(storageSourceProperty, storageProperty, document -> {
            booleanCallback.done(document != null && !document.isEmpty());
        });
    }

    @Override
    public void close() {
        this.mongoClient.close();
    }

    @Override
    public @NotNull String getReadyTranslationProperty() {
        return "autumn.storage.mongo.ready";
    }

    private MongoCollection<Document> getMongoCollection(StorageSourceProperty storageSourceProperty) {
        return this.mongoDatabase.getCollection(storageSourceProperty.name());
    }
}
