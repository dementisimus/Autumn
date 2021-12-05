package dev.dementisimus.autumn.common.api.database.property;

import org.bson.Document;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class UpdateDataProperty @ Autumn
 *
 * @author dementisimus
 * @since 04.12.2021:14:49
 */
public interface UpdateDataProperty extends DataProperty {

    UpdateDataProperty value(String name, Object value);

    String name();

    Object value();

    Document document();

    Document fullDocument();
}
