/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.storage.property;

import dev.dementisimus.autumn.api.storage.property.StorageUpdateProperty;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomStorageUpdateProperty extends CustomStorageProperty implements StorageUpdateProperty {

    private String name;
    private Object value;

    public CustomStorageUpdateProperty(String fieldName, Object fieldValue) {
        super(fieldName, fieldValue);
    }

    public static StorageUpdateProperty of(String fieldName, Object fieldValue) {
        return new CustomStorageUpdateProperty(fieldName, fieldValue);
    }

    @Override
    public @NotNull StorageUpdateProperty value(@Nullable String name, @Nullable Object value) {
        this.name = name;
        this.value = value;

        return this;
    }

    @Override
    public @Nullable String name() {
        return this.name;
    }

    @Override
    public @Nullable Object value() {
        return this.value;
    }

    @Override
    public @NotNull Document document() {
        return new Document("$set", new Document(this.name, this.value));
    }

    @Override
    public @NotNull Document fullDocument() {
        Document document = new Document();

        document.put(this.fieldName(), this.fieldValue());
        document.put(this.name, this.value);

        return document;
    }
}
