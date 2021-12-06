/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.dependency;

/**
 * Represents a dependency
 *
 * @since 1.0.0
 */
public interface AutumnDependency {

    /**
     * Gets the {@link AutumnRepository} for the dependency
     *
     * @return the {@link AutumnRepository} for the dependency
     *
     * @since 1.0.0
     */
    AutumnRepository getRepository();

    /**
     * Sets the {@link AutumnRepository} for the dependency
     *
     * @param repository the {@link AutumnRepository} for the dependency
     *
     * @since 1.0.0
     */
    void setRepository(AutumnRepository repository);

    /**
     * Gets the dependency group id
     *
     * @return dependency group id
     *
     * @since 1.0.0
     */
    String getGroupId();

    /**
     * Sets the dependency group id
     *
     * @param groupId the dependency group id
     *
     * @since 1.0.0
     */
    void setGroupId(String groupId);

    /**
     * Gets the dependency artifact id
     *
     * @return dependency artifact id
     *
     * @since 1.0.0
     */
    String getArtifactId();

    /**
     * Sets the dependency artifact id
     *
     * @param artifactId the dependency artifact id
     *
     * @since 1.0.0
     */
    void setArtifactId(String artifactId);

    /**
     * Gets the dependency version
     *
     * @return dependency version
     *
     * @since 1.0.0
     */
    String getVersion();

    /**
     * Sets the dependency version
     *
     * @param version dependency version
     *
     * @since 1.0.0
     */
    void setVersion(String version);

    /**
     * Transforms {@link #getArtifactId()} + {@link #getVersion()} into a readable file name
     * Example: Autumn-1.0.0
     *
     * @return readable file name (ArtifactID-Version)
     *
     * @since 1.0.0
     */
    String getFileName();

    /**
     * Transforms the dependency and its repository into an URL
     *
     * @return thr URL for the dependency
     *
     * @since 1.0.0
     */
    String toURL();
}
