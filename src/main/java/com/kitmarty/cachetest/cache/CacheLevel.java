package com.kitmarty.cachetest.cache;

import com.kitmarty.cachetest.storage.Storage;
import com.kitmarty.cachetest.strategy.Strategy;

import java.util.*;

/**
 * CacheLevelLevel is implementation of Cache Interface, that allows to organize one cache level.
 *
 * @param <K> the type of keys maintained by this cache level
 * @param <V> the type of values contained in this cache level
 */
public class CacheLevel<K, V> implements Cache<K, V> {

    private final Storage<K, V> storage;
    private final Strategy<K> strategy;

    public CacheLevel(Storage<K, V> storage, Strategy<K> strategy) {
        Objects.requireNonNull(storage, "CacheLevel storage null");
        Objects.requireNonNull(strategy, "CacheLevel strategy null");
        this.storage = storage;
        this.strategy = strategy;
    }

    @Override
    public Optional<Map.Entry<K, V>> put(K key, V value) {
        Objects.requireNonNull(key, "CacheLevel key null");
        Objects.requireNonNull(value, "CacheLevel value null");
        Optional<K> displacedKey;
        Optional<V> displacedValue;
        AbstractMap.SimpleEntry<K, V> displacedEntry;

        displacedKey = strategy.put(key);
        if (displacedKey.isPresent()) {
            displacedValue = storage.remove(displacedKey.get());
            displacedEntry = new AbstractMap.SimpleEntry<K, V>(displacedKey.get(), displacedValue.get());
            storage.put(key, value);
            return Optional.ofNullable(displacedEntry);
        }
        storage.put(key, value);
        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {
        if (strategy.update(key)) {
            return storage.get(key);
        }
        return Optional.empty();
    }

    @Override
    public boolean containsKey(K key) {
        return strategy.containsKey(key);
    }
}
