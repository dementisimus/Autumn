package dev.dementisimus.autumn.common.dependency;

import dev.dementisimus.autumn.common.api.dependency.AutumnRepository;
import lombok.RequiredArgsConstructor;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnRepository @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:18:32
 */
@RequiredArgsConstructor
public class DefaultAutumnRepository implements AutumnRepository {

    private String name;
    private String url;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public void setURL(String url) {
        if(!url.endsWith("/")) url += "/";

        this.url = url;
    }
}
