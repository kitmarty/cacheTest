package com.kitmarty.cachetest;

import com.kitmarty.storage.MemoryStorage;
import com.kitmarty.storage.Storage;
import com.kitmarty.strategy.FIFOStrategy;
import com.kitmarty.strategy.LRUStrategy;
import com.kitmarty.strategy.Strategy;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * CacheLevelLevel is implementation of Cache Interface, that allows to organize one cache level.
 * @param <K> the type of keys maintained by this cache level
 * @param <V> the type of values contained in this cache level
 */
public class CacheLevel<K,V> implements Cache<K,V> {

    private final Storage<K,V> storage;
    private final Strategy<K> strategy;

    public CacheLevel(Storage<K, V> storage, Strategy<K> strategy) {
        this.storage = storage;
        this.strategy = strategy;
    }

    @Override
    public Optional<Map.Entry<K, V>> put(K key, V value) {
        Optional<K> displacedKey;
        Optional<V> displacedValue;
        AbstractMap.SimpleEntry<K,V> displacedEntry;

        displacedKey = strategy.put(key);
        if (displacedKey.isPresent()) {
            displacedValue = storage.remove(displacedKey.get());
            displacedEntry = new AbstractMap.SimpleEntry<K,V>(displacedKey.get(), displacedValue.get());
            storage.put(key,value);
            return Optional.ofNullable(displacedEntry);
        }
        storage.put(key,value);
        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {
        if (strategy.update(key)) {
            return storage.get(key);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public boolean containsKey(K key) {
        return strategy.containsKey(key);
    }

    @Override
    public String toString() {
        return "CacheLevel{" +
                "storage=" + storage.toString() +
                ", strategy=" + strategy +
                '}';
    }

    public static void main(String[] args) {
        Cache<Integer,String> cache =
                new CacheLevel<Integer, String>(new MemoryStorage<>(new HashMap<>()), new LRUStrategy<>(3));
        cache.put(1,"Один");
        cache.put(2,"Два");
        cache.put(3,"Три");
        cache.put(4,"Четыре");
        cache.get(2);
        cache.put(5,"Пять");
        cache.put(6,"Шесть");
        System.out.print(cache.toString());
    }
}
