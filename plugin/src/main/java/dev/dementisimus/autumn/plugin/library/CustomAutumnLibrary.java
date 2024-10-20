/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.library;

import dev.dementisimus.autumn.api.library.AutumnLibrary;
import dev.dementisimus.autumn.api.library.AutumnRepository;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CustomAutumnLibrary implements AutumnLibrary {

    private CustomAutumnRepository repository = new CustomAutumnRepository("maven-central", "https://repo1.maven.org/maven2/");
    private String groupId;
    private String artifactId;
    private String version;

    @Override
    public AutumnRepository repository() {
        return this.repository;
    }

    @Override
    public void repository(AutumnRepository repository) {
        this.repository = (CustomAutumnRepository) repository;
    }

    @Override
    public String groupId() {
        return this.groupId;
    }

    @Override
    public void groupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String artifactId() {
        return this.artifactId;
    }

    @Override
    public void artifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    @Override
    public String version() {
        return this.version;
    }

    @Override
    public void version(String version) {
        this.version = version;
    }

    @Override
    public String fileName() {
        return this.artifactId + "-" + this.version + ".jar";
    }

    @Override
    public @NotNull String toURL() {
        if (this.groupId.contains(".")) this.groupId = this.groupId.replaceAll("\\.", "/");

        return this.repository.url() + this.groupId + "/" + this.artifactId + "/" + this.version + "/" + this.fileName();
    }
}
