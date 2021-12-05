package dev.dementisimus.autumn.common.api.map;

import dev.dementisimus.autumn.common.api.callback.AutumnTriCallback;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class TripleMap<A, B, C> {

    private final Map<A, Map<B, C>> map = new WeakHashMap<>();

    public C put(A a, B b, C c) {
        if(!this.map.containsKey(a)) {
            this.map.put(a, new WeakHashMap<>());
        }
        this.map.get(a).put(b, c);
        return c;
    }

    public C remove(A a, B b) {
        if(!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).remove(b);
    }

    public boolean isEmpty(A a) {
        if(!this.map.containsKey(a)) {
            return true;
        }
        return this.map.get(a).isEmpty();
    }

    public C get(A a, B b) {
        if(!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).get(b);
    }

    public boolean containsKey(A a) {
        return this.map.containsKey(a);
    }

    public boolean containsKey(A a, B b) {
        if(!this.map.containsKey(a)) {
            return false;
        }
        return this.map.get(a).containsKey(b);
    }

    public boolean containsValue(A a, B c) {
        if(!this.map.containsKey(a)) {
            return false;
        }
        return this.map.get(a).containsValue(c);
    }

    public Collection<Map<B, C>> values() {
        return this.map.values();
    }

    public Collection<C> values(A a) {
        if(!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).values();
    }

    public Set<A> keySet() {
        return this.map.keySet();
    }

    public Set<B> keySet(A a) {
        if(!this.map.containsKey(a)) {
            return null;
        }
        return this.map.get(a).keySet();
    }

    public void forEach(AutumnTriCallback<? super A, ? super B, ? super C> cb) {
        Map<A, Map<B, C>> finalMap = new HashMap<>(this.map);
        Map<A, Map<B, C>> finalMap1 = finalMap;
        finalMap.keySet().forEach(a -> finalMap1.get(a).keySet().forEach(b -> cb.done(a, b, finalMap1.get(a).get(b))));
        finalMap = new HashMap<>();
    }
}
