/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.storage.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bson.Document;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StorageCache {

    private static final Cache<String, Document> CACHED_DOCUMENTS = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();
    private static final Cache<String, List<Document>> CACHED_DOCUMENT_LISTS = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

    public static Document get(String key) {
        return CACHED_DOCUMENTS.getIfPresent(key);
    }

    public static List<Document> getList(String key) {
        return CACHED_DOCUMENT_LISTS.getIfPresent(key);
    }

    public static void set(String key, Document document) {
        CACHED_DOCUMENTS.put(key, document);
    }

    public static void setList(String key, List<Document> documents) {
        CACHED_DOCUMENT_LISTS.put(key, documents);
    }

    public static void invalidate(String key) {
        CACHED_DOCUMENTS.invalidate(key);
        CACHED_DOCUMENT_LISTS.invalidate(key);
    }
}
