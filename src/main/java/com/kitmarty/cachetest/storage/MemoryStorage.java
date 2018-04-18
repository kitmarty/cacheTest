package com.kitmarty.cachetest.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MemoryStorage<K, V> implements Storage<K, V> {

    private final Map<K, V> objectStorage;

    public static <K, V> MemoryStorage<K, V> ofHashMap() {
        return new MemoryStorage<>(new HashMap<>());
    }

    private MemoryStorage(Map<K, V> objectStorage) {
        Objects.requireNonNull(objectStorage, "objectStorage");
        this.objectStorage = objectStorage;
    }

    @Override
    public Optional<V> put(K key, V value) {
        return Optional.ofNullable(objectStorage.put(key, value));
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(objectStorage.get(key));
    }

    @Override
    public Optional<V> remove(K key) {
        return Optional.ofNullable(objectStorage.remove(key));
    }
}