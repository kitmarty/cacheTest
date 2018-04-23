package com.kitmarty.cachetest.storage;

import java.io.Serializable;
import java.util.Optional;

public class DiskStorage<K, V extends Serializable> implements Storage<K, V> {

    @Override
    public Optional<V> put(K key, V value) {
        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }

    @Override
    public Optional<V> remove(K key) {
        return Optional.empty();
    }
}
