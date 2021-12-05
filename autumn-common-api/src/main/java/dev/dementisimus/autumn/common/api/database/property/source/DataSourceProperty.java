package dev.dementisimus.autumn.common.api.database.property.source;

import java.util.Map;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DataSourceProperty @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:14:59
 */
public interface DataSourceProperty {

    String name();

    Map<String, String> fields();
}
