/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.dependency;

import dev.dementisimus.autumn.common.api.dependency.AutumnDependency;
import dev.dementisimus.autumn.common.api.dependency.AutumnRepository;
import org.jetbrains.annotations.NotNull;

public class CustomAutumnDependency implements AutumnDependency {

    private AutumnRepository repository;
    private String groupId;
    private String artifactId;
    private String version;

    @Override
    public AutumnRepository repository() {
        return this.repository;
    }

    @Override
    public void repository(@NotNull AutumnRepository repository) {
        this.repository = repository;
    }

    @Override
    public String groupId() {
        return this.groupId;
    }

    @Override
    public void groupId(@NotNull String groupId) {
        if(groupId.contains(".")) groupId = groupId.replaceAll("\\.", "/");

        this.groupId = groupId;
    }

    @Override
    public String artifactId() {
        return this.artifactId;
    }

    @Override
    public void artifactId(@NotNull String artifactId) {
        this.artifactId = artifactId;
    }

    @Override
    public String version() {
        return this.version;
    }

    @Override
    public void version(@NotNull String version) {
        this.version = version;
    }

    @Override
    public String fileName() {
        return this.artifactId + "-" + this.version + ".jar";
    }

    @Override
    public @NotNull String toURL() {
        StringBuilder urlBuilder = new StringBuilder(this.repository.url());

        urlBuilder.append(this.groupId);
        urlBuilder.append("/");
        urlBuilder.append(this.artifactId);
        urlBuilder.append("/");
        urlBuilder.append(this.version);
        urlBuilder.append("/");
        urlBuilder.append(this.fileName());

        return urlBuilder.toString();
    }
}
