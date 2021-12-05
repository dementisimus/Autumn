package dev.dementisimus.autumn.common.api.database.property;

import org.bson.conversions.Bson;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DataProperty @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:15:03
 */
public interface DataProperty {

    String fieldName();

    Object fieldValue();

    Bson filter();

    String toString();
}
