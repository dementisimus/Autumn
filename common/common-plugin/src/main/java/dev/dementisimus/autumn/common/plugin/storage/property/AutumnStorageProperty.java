/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.storage.property;

import dev.dementisimus.autumn.common.api.storage.property.StorageProperty;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class AutumnStorageProperty implements StorageProperty {

    private final String fieldName;
    private final Object fieldValue;

    public static StorageProperty of(String fieldName, Object fieldValue) {
        return new AutumnStorageUpdateProperty(fieldName, fieldValue);
    }

    @Override
    public @NotNull String fieldName() {
        return this.fieldName;
    }

    @Override
    public @NotNull Object fieldValue() {
        return this.fieldValue;
    }

    @Override
    public @NotNull Bson filter() {
        return new Document(this.fieldName, this.fieldValue);
    }

    @Override
    public @NotNull String toString() {
        return "StorageProperty{" + "fieldName='" + this.fieldName + '\'' + ", fieldValue=" + this.fieldValue + '}';
    }
}
