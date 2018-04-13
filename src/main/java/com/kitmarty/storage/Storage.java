package com.kitmarty.storage;

import java.util.Optional;

public interface Storage<K, V> {

    Optional<V> put(K key, V value);

    Optional<V> get(K key);

    Optional<V> remove(K key);
}
