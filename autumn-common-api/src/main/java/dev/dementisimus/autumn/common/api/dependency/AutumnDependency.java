package dev.dementisimus.autumn.common.api.dependency;

/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnDependency @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:18:58
 */
public interface AutumnDependency {

    AutumnRepository getRepository();

    void setRepository(AutumnRepository repository);

    String getGroupId();

    void setGroupId(String groupId);

    String getArtifactId();

    void setArtifactId(String artifactId);

    String getVersion();

    void setVersion(String version);

    String getFileName();

    String toURL();
}
