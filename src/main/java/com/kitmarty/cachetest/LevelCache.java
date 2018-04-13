package com.kitmarty.cachetest;

import com.kitmarty.storage.Storage;
import com.kitmarty.strategy.Strategy;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class LevelCache<K,V> implements Cache<K,V> {

    private final Storage<K,V> storage;
    private final Strategy<K> strategy;

    public LevelCache(Storage<K, V> storage, Strategy<K> strategy) {
        this.storage = storage;
        this.strategy = strategy;
    }

    @Override
    public Optional<Map.Entry<K, V>> put(K key, V value) {
        Optional<K> displacedKey;
        Optional<V> displacedValue;
        AbstractMap.SimpleEntry<K,V> displacedEntry;

        displacedKey = strategy.put(key);
        if (displacedKey.isPresent()){
            displacedValue = storage.remove((K) displacedKey);
            displacedEntry = new AbstractMap.SimpleEntry<K,V>((K) displacedKey, (V) displacedValue);
            return Optional.ofNullable(displacedEntry);
        }
        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {
        strategy.update(key);
        return storage.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }
}
