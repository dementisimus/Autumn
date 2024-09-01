package dev.dementisimus.autumn.api.map;

import dev.dementisimus.autumn.api.callback.TripleCallback;

import java.util.*;

/**
 * @param <A> a
 * @param <B> b
 * @since 1.0.0
 */
public class TripleMap<A, B, C> {

    private final Map<A, Map<B, C>> map = new WeakHashMap<>();

    /**
     * @param a a
     * @param b b
     * @param c c
     * @return C
     * @since 1.0.0
     */
    public C put(A a, B b, C c) {
        if (!this.map.containsKey(a)) {
            this.map.put(a, new WeakHashMap<>());
        }
        this.map.get(a).put(b, c);
        return c;
    }

    /**
     * @param a a
     * @param b b
     * @return C
     */
    public C remove(A a, B b) {
        if (!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).remove(b);
    }

    /**
     * @param a a
     * @return true, if success, false otherwise
     */
    public boolean isEmpty(A a) {
        if (!this.map.containsKey(a)) {
            return true;
        }
        return this.map.get(a).isEmpty();
    }

    /**
     * @param a a
     * @param b c
     * @return C
     * @since 1.0.0
     */
    public C get(A a, B b) {
        if (!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).get(b);
    }

    /**
     * @param a a
     * @return true, if map contains key, false otherwise
     * @since 1.0.0
     */
    public boolean containsKey(A a) {
        return this.map.containsKey(a);
    }

    /**
     * @param a a
     * @param b b
     * @return true, if map contains keys, false otherwise
     * @since 1.0.0
     */
    public boolean containsKey(A a, B b) {
        if (!this.map.containsKey(a)) {
            return false;
        }
        return this.map.get(a).containsKey(b);
    }

    /**
     * @param a a
     * @param c c
     * @return true, if map contains keys, false otherwise
     * @since 1.0.0
     */
    public boolean containsValue(A a, B c) {
        if (!this.map.containsKey(a)) {
            return false;
        }
        return this.map.get(a).containsValue(c);
    }

    /**
     * @return collection
     * @since 1.0.0
     */
    public Collection<Map<B, C>> values() {
        return this.map.values();
    }

    /**
     * @param a a
     * @return collection
     * @since 1.0.0
     */
    public Collection<C> values(A a) {
        if (!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).values();
    }

    /**
     * @return set
     * @since 1.0.0
     */
    public Set<A> keySet() {
        return this.map.keySet();
    }

    /**
     * @param a a
     * @return set
     * @since 1.0.0
     */
    public Set<B> keySet(A a) {
        if (!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).keySet();
    }

    /**
     * @param cb cb
     * @since 1.0.0
     */
    public void forEach(TripleCallback<? super A, ? super B, ? super C> cb) {
        Map<A, Map<B, C>> finalMap = new HashMap<>(this.map);
        Map<A, Map<B, C>> finalMap1 = finalMap;
        finalMap.keySet().forEach(a -> finalMap1.get(a).keySet().forEach(b -> cb.done(a, b, finalMap1.get(a).get(b))));
        finalMap = new HashMap<>();
    }
}
