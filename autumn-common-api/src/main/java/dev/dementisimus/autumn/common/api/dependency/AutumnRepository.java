package dev.dementisimus.autumn.common.api.dependency;

/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnRepository @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:18:58
 */
public interface AutumnRepository {

    String getName();

    void setName(String name);

    String getURL();

    void setURL(String url);
}
