/*
 | Copyright 2024 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.bukkit.plugin.reflection;

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class AutumnReflectionUtils {

    private static final MethodHandles.Lookup LOOKUP;

    static {
        MethodHandles.Lookup lookup1;
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);

            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            Field lookup = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");

            lookup1 = (MethodHandles.Lookup) unsafe.getObject(unsafe.staticFieldBase(lookup), unsafe.staticFieldOffset(lookup));
        } catch (IllegalAccessException | NoSuchFieldException ignored) {
            lookup1 = null;
        }
        LOOKUP = lookup1;
    }

    public static void setAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static MethodHandles.Lookup getLookup() {
        return LOOKUP;
    }
}
