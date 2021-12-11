/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.dependency;

import dev.dementisimus.autumn.common.api.dependency.AutumnRepository;
import org.jetbrains.annotations.NotNull;

public class CustomAutumnRepository implements AutumnRepository {

    private String name;
    private String url;

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void name(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public void url(@NotNull String url) {
        if(!url.endsWith("/")) url += "/";

        this.url = url;
    }
}
