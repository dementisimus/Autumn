/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.injection;

import com.google.common.reflect.ClassPath;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.dementisimus.autumn.common.api.injection.AutumnInjector;
import dev.dementisimus.autumn.common.api.injection.module.GenericInjectionModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DefaultAutumnInjector implements AutumnInjector {

    private static final Map<ClassLoader, List<Class<?>>> CLASSLOADER_CLASSES = new HashMap<>();
    private static final Map<Class<?>, GenericInjectionModule<?>> GENERIC_INJECTION_MODULES = new HashMap<>();
    private static final List<String> INJECTED_CLASSES = new ArrayList<>();
    private static final List<ClassLoader> CLASSLOADERS = new ArrayList<>();
    private static final List<Class<? extends Annotation>> ANNOTATIONS = new ArrayList<>();
    private static int LAST_MODULES_LIST_SIZE;
    private static Injector INJECTOR;

    public abstract void register(Class<? extends Annotation> annotation, Class<?> clazz, Injector injector);

    @Override
    public @NotNull AutumnInjector classLoaders(@NotNull ClassLoader... classLoaders) {
        CLASSLOADERS.addAll(List.of(classLoaders));
        return this;
    }

    @SafeVarargs
    @Override
    public final @NotNull AutumnInjector annotation(@NotNull Class<? extends Annotation>... annotations) {
        ANNOTATIONS.addAll(List.of(annotations));
        return this;
    }

    @Override
    public void scan() {
        for(ClassLoader classLoader : CLASSLOADERS) {
            if(!CLASSLOADER_CLASSES.containsKey(classLoader)) {
                try {
                    Stream<ClassPath.ClassInfo> classInfoStream = ClassPath.from(classLoader).getAllClasses().stream();
                    List<ClassPath.ClassInfo> availableClassLoaderClasses = classInfoStream.filter(classInfo -> classInfo.getPackageName().startsWith("dev.dementisimus")).collect(Collectors.toList());
                    List<Class<?>> classLoaderClasses = new ArrayList<>();

                    for(ClassPath.ClassInfo classInfo : availableClassLoaderClasses) {
                        try {
                            Class<?> clazz = Class.forName(classInfo.getName());
                            classLoaderClasses.add(clazz);
                        }catch(NoClassDefFoundError | ClassNotFoundException ignored) {}
                    }

                    CLASSLOADER_CLASSES.put(classLoader, classLoaderClasses);
                }catch(IOException ignored) {}
            }
        }

        if(INJECTOR == null || LAST_MODULES_LIST_SIZE != GENERIC_INJECTION_MODULES.size()) {
            INJECTOR = Guice.createInjector(GENERIC_INJECTION_MODULES.values());
            LAST_MODULES_LIST_SIZE = GENERIC_INJECTION_MODULES.size();
        }

        for(ClassLoader classloader : CLASSLOADERS) {
            for(Class<?> clazz : CLASSLOADER_CLASSES.get(classloader)) {
                String className = clazz.getCanonicalName();

                if(!INJECTED_CLASSES.contains(className)) {
                    if(!Arrays.asList(clazz.getAnnotations()).isEmpty()) {
                        for(Class<? extends Annotation> annotation : ANNOTATIONS) {
                            if(clazz.isAnnotationPresent(annotation)) {
                                this.register(annotation, clazz, INJECTOR);
                                INJECTED_CLASSES.add(className);
                            }
                        }
                    }else {
                        INJECTED_CLASSES.add(className);
                    }
                }
            }
        }
    }

    @Override
    public <T> void registerModule(@NotNull Class<T> clazz, @Nullable T value) {
        GENERIC_INJECTION_MODULES.put(clazz, new GenericInjectionModule<>(clazz, value));
    }
}
