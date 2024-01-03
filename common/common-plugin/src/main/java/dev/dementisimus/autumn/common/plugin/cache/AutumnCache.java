/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.cache;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class AutumnCache {

    private static final Cache<String, Object> CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public static <T> T get(String key, Class<T> clazz) {
        Preconditions.checkNotNull(key, "Cache key may not be null!");
        Preconditions.checkNotNull(key, "Cache key class may not be null!");

        Object obj = CACHE.getIfPresent(key);

        if (obj != null) {
            if (!obj.getClass().equals(clazz)) {
                throw new IllegalArgumentException("Cache key '" + key + "' does not store " + clazz.getName() + "!");
            }
        }

        return (T) CACHE.getIfPresent(key);
    }

    public static <T> void set(String key, T object) {
        Preconditions.checkNotNull(key, "Cache key may not be null!");
        Preconditions.checkNotNull(key, "Cache object may not be null!");

        CACHE.put(key, object);
    }

    public static void invalidate(String key) {
        CACHE.invalidate(key);
    }
}
