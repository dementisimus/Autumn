package dev.dementisimus.autumn.common.database.property;

import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnDataProperty @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:14:54
 */
@RequiredArgsConstructor
public class AutumnDataProperty implements DataProperty {

    private final String fieldName;
    private final Object fieldValue;

    public static DataProperty of(String fieldName, Object fieldValue) {
        return new AutumnUpdateDataProperty(fieldName, fieldValue);
    }

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
}
