/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.dependency;

import dev.dementisimus.autumn.common.api.dependency.AutumnRepository;
import lombok.RequiredArgsConstructor;

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
