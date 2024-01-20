/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.reflection;

import lombok.RequiredArgsConstructor;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@RequiredArgsConstructor
public class AutumnReflectionMethod<C, T> {

    private final MethodHandle handle;

    public static <C, T> AutumnReflectionMethod<C, T> getterNonStatic(Class<? extends C> owner, String fieldName, Class<T> fieldType) {
        Field field = AutumnReflectionField.findAnyField(owner, fieldName, fieldType);
        if (field != null && !Modifier.isStatic(field.getModifiers())) {
            AutumnReflectionUtils.setAccessible(field);

            try {
                return new AutumnReflectionMethod<>(AutumnReflectionUtils.getLookup().unreflectGetter(field));
            } catch (IllegalAccessException ignored) {
            }
        }

        return null;
    }

    public static <C, T> AutumnReflectionMethod<C, T> getterStatic(Class<? extends C> owner, String fieldName, Class<T> fieldType) {
        Field field = AutumnReflectionField.findAnyField(owner, fieldName, fieldType);
        if (field != null && Modifier.isStatic(field.getModifiers())) {
            AutumnReflectionUtils.setAccessible(field);

            try {
                return new AutumnReflectionMethod<>(AutumnReflectionUtils.getLookup().unreflectGetter(field));
            } catch (IllegalAccessException ignored) {
            }
        }

        return null;
    }

    public T get(C instance) {
        try {
            return (T) this.handle.invoke(instance);
        } catch (Throwable var3) {
            throw new RuntimeException(var3);
        }
    }

    public T get() {
        try {
            return (T) this.handle.invoke();
        } catch (Throwable var3) {
            throw new RuntimeException(var3);
        }
    }
}
