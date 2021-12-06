/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.database.type.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import dev.dementisimus.autumn.common.api.database.type.DatabaseType;
import dev.dementisimus.autumn.common.database.type.mongo.subscriber.DeleteResultSubscriber;
import dev.dementisimus.autumn.common.database.type.mongo.subscriber.DocumentListSubscriber;
import dev.dementisimus.autumn.common.database.type.mongo.subscriber.DocumentSubscriber;
import dev.dementisimus.autumn.common.database.type.mongo.subscriber.InsertSubscriber;
import dev.dementisimus.autumn.common.database.type.mongo.subscriber.UpdateSubscriber;
import dev.dementisimus.autumn.common.setup.state.MainSetupStates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDatabase implements DatabaseType {

    private final com.mongodb.reactivestreams.client.MongoDatabase mongoDatabase;
    private final MongoClient mongoClient;

    public MongoDatabase() {
        String uri = MainSetupStates.MONGODB_URI.asString();
        String databaseName = MainSetupStates.DATABASE.asString();

        this.mongoClient = MongoClients.create(uri);
        this.mongoDatabase = this.mongoClient.getDatabase(databaseName);
    }

    @Override
    public void read(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Document> documentCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(dataSourceProperty);

        mongoCollection.find(dataProperty.filter()).first().subscribe(new DocumentSubscriber(documentCallback));
    }

    @Override
    public void list(DataSourceProperty dataSourceProperty, AutumnCallback<List<Document>> listDocumentCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(dataSourceProperty);

        mongoCollection.find().subscribe(new DocumentListSubscriber(new ArrayList<>(), listDocumentCallback));
    }

    @Override
    public void write(DataSourceProperty dataSourceProperty, Document document, AutumnCallback<Boolean> booleanCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(dataSourceProperty);

        mongoCollection.insertOne(document).subscribe(new InsertSubscriber(booleanCallback));
    }

    @Override
    public void update(DataSourceProperty dataSourceProperty, UpdateDataProperty updateDataProperty, AutumnCallback<Boolean> booleanCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(dataSourceProperty);

        mongoCollection.updateOne(updateDataProperty.filter(), updateDataProperty.document()).subscribe(new UpdateSubscriber(booleanCallback));
    }

    @Override
    public void delete(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback) {
        MongoCollection<Document> mongoCollection = this.getMongoCollection(dataSourceProperty);

        mongoCollection.deleteOne(dataProperty.filter()).subscribe(new DeleteResultSubscriber(booleanCallback));
    }

    @Override
    public void isPresent(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback) {
        this.read(dataSourceProperty, dataProperty, document -> {
            booleanCallback.done(document != null && !document.isEmpty());
        });
    }

    @Override
    public void close() {
        this.mongoClient.close();
    }

    @Override
    public String readyTranslationProperty() {
        return "autumn.database.mongo.ready";
    }

    private MongoCollection<Document> getMongoCollection(DataSourceProperty dataSourceProperty) {
        return this.mongoDatabase.getCollection(dataSourceProperty.name());
    }
}
