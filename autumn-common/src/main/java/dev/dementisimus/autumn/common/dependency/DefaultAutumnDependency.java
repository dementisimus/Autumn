/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.dependency;

import dev.dementisimus.autumn.common.api.dependency.AutumnDependency;
import dev.dementisimus.autumn.common.api.dependency.AutumnRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultAutumnDependency implements AutumnDependency {

    private AutumnRepository repository;
    private String groupId;
    private String artifactId;
    private String version;

    @Override
    public @Nullable AutumnRepository getRepository() {
        return this.repository;
    }

    @Override
    public void setRepository(@NotNull AutumnRepository repository) {
        this.repository = repository;
    }

    @Override
    public @Nullable String getGroupId() {
        return this.groupId;
    }

    @Override
    public void setGroupId(@NotNull String groupId) {
        if(groupId.contains(".")) groupId = groupId.replaceAll("\\.", "/");

        this.groupId = groupId;
    }

    @Override
    public @Nullable String getArtifactId() {
        return this.artifactId;
    }

    @Override
    public void setArtifactId(@NotNull String artifactId) {
        this.artifactId = artifactId;
    }

    @Override
    public @Nullable String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(@NotNull String version) {
        this.version = version;
    }

    @Override
    public @Nullable String getFileName() {
        return this.artifactId + "-" + this.version + ".jar";
    }

    @Override
    public @NotNull String toURL() {
        StringBuilder urlBuilder = new StringBuilder(this.repository.getURL());

        urlBuilder.append(this.groupId);
        urlBuilder.append("/");
        urlBuilder.append(this.artifactId);
        urlBuilder.append("/");
        urlBuilder.append(this.version);
        urlBuilder.append("/");
        urlBuilder.append(this.getFileName());

        return urlBuilder.toString();
    }
}
