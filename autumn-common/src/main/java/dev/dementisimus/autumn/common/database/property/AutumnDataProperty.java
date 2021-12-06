/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.database.property;

import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;

@RequiredArgsConstructor
public class AutumnDataProperty implements DataProperty {

    private final String fieldName;
    private final Object fieldValue;

    @Override
    public String fieldName() {
        return this.fieldName;
    }

    @Override
    public Object fieldValue() {
        return this.fieldValue;
    }

    @Override
    public Bson filter() {
        return new Document(this.fieldName, this.fieldValue);
    }

    @Override
    public String toString() {
        return "DataProperty{" + "fieldName='" + this.fieldName + '\'' + ", fieldValue=" + this.fieldValue + '}';
    }

    public static DataProperty of(String fieldName, Object fieldValue) {
        return new AutumnUpdateDataProperty(fieldName, fieldValue);
    }
}
