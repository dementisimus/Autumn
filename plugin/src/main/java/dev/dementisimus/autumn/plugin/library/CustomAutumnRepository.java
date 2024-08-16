/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.library;

import dev.dementisimus.autumn.api.library.AutumnRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomAutumnRepository implements AutumnRepository {

    private String name;
    private String url;

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void name(String name) {
        this.name = name;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public void url(String url) {
        if (!url.endsWith("/")) url += "/";

        this.url = url;
    }
}
