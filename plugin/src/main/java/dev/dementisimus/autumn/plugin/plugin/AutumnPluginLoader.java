/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.dementisimus.autumn.plugin.CustomAutumn;
import dev.dementisimus.autumn.plugin.library.CustomAutumnLibrary;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import lombok.Data;
import lombok.SneakyThrows;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AutumnPluginLoader implements PluginLoader {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    @SneakyThrows
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        try (JarFile jarFile = new JarFile(new File(CustomAutumn.class.getProtectionDomain().getCodeSource().getLocation().toURI()))) {
            JarEntry jarEntry = jarFile.getJarEntry("libraries.json");
            JsonElement element = JsonParser.parseReader(new InputStreamReader(jarFile.getInputStream(jarEntry)));

            for (CustomAutumnLibrary library : GSON.fromJson(element, Libraries.class).getLibraries()) {
                MavenLibraryResolver resolver = new MavenLibraryResolver();
                resolver.addDependency(new Dependency(new DefaultArtifact(library.groupId() + ":" + library.artifactId() + ":" + library.version()), null));
                resolver.addRepository(new RemoteRepository.Builder(library.repository().name(), "default", library.repository().url()).build());

                classpathBuilder.addLibrary(resolver);
            }
        }
    }

    @Data
    static class Libraries {
        private Set<CustomAutumnLibrary> libraries = Set.of();
    }
}
