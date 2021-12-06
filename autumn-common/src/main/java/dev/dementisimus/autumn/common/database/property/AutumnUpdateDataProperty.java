package dev.dementisimus.autumn.common.database.property;

import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import org.bson.Document;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnUpdateDataProperty @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:14:54
 */
public class AutumnUpdateDataProperty extends AutumnDataProperty implements UpdateDataProperty {

    private String name;
    private Object value;

    public AutumnUpdateDataProperty(String fieldName, Object fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    public UpdateDataProperty value(String name, Object value) {
        this.name = name;
        this.value = value;

        return this;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public Document document() {
        return new Document("$set", new Document(this.name, this.value));
    }

    @Override
    public Document fullDocument() {
        Document document = new Document();

        document.put(this.fieldName(), this.fieldValue());
        document.put(this.name, this.value);

        return document;
    }

    public static UpdateDataProperty of(String fieldName, Object fieldValue) {
        return new AutumnUpdateDataProperty(fieldName, fieldValue);
    }
}
