package dev.dementisimus.autumn.plugin.reflection;

import lombok.RequiredArgsConstructor;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@RequiredArgsConstructor
public class ReflectionMethod<C, T> {

    private final MethodHandle handle;

    public static <C, T> ReflectionMethod<C, T> getterNonStatic(Class<? extends C> owner, String fieldName, Class<T> fieldType) {
        Field field = ReflectionField.findAnyField(owner, fieldName, fieldType);
        if (field != null && !Modifier.isStatic(field.getModifiers())) {
            ReflectionUtils.setAccessible(field);

            try {
                return new ReflectionMethod<>(ReflectionUtils.getLookup().unreflectGetter(field));
            } catch (IllegalAccessException ignored) {
            }
        }

        return null;
    }

    public static <C, T> ReflectionMethod<C, T> getterStatic(Class<? extends C> owner, String fieldName, Class<T> fieldType) {
        Field field = ReflectionField.findAnyField(owner, fieldName, fieldType);
        if (field != null && Modifier.isStatic(field.getModifiers())) {
            ReflectionUtils.setAccessible(field);

            try {
                return new ReflectionMethod<>(ReflectionUtils.getLookup().unreflectGetter(field));
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
