package com.kitmarty.cachetest.cache;

import com.kitmarty.cachetest.storage.MemoryStorage;
import com.kitmarty.cachetest.strategy.Strategy;

import java.util.*;

public class MultiLevelCache<K, V> implements Cache<K, V> {

    private final LinkedHashSet<CacheLevel<K, V>> cacheList = new LinkedHashSet<>();

    public MultiLevelCache() {
    }

    public MultiLevelCache(LinkedHashSet<CacheLevel<K, V>> cacheList) {
        this.cacheList.addAll(cacheList);
    }

    public void addLevel(CacheLevel<K, V> cacheLevel) {
        cacheList.add(cacheLevel);
    }

    @Override
    public Optional<Map.Entry<K, V>> put(K key, V value) {
        Iterator<CacheLevel<K, V>> it;
        if (!cacheList.isEmpty()) {
            remove(key);
            it = cacheList.iterator();
            while (it.hasNext()) {
                Optional<Map.Entry<K, V>> displacedEntry = it.next().put(key, value);
                if (!displacedEntry.isPresent()) {
                    break;
                }
                key = displacedEntry.get().getKey();
                value = displacedEntry.get().getValue();
            }
            return Optional.ofNullable(new AbstractMap.SimpleEntry<K, V>(key, value));
        }
        throw new NullPointerException();
    }

    @Override
    public Optional<V> get(K key) {
        Iterator<CacheLevel<K, V>> it;
        Optional<V> removedValue;
        if (!cacheList.isEmpty()) {
            removedValue = remove(key);
            if (removedValue.isPresent()) {
                put(key, removedValue.get());
                return removedValue;
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean containsKey(K key) {
        Iterator<CacheLevel<K, V>> it = cacheList.iterator();
        while (it.hasNext()) {
            CacheLevel<K, V> cacheLevel = it.next();
            if (cacheLevel.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    private Optional<V> remove(K key) {
        Iterator<CacheLevel<K, V>> it = cacheList.iterator();
        while (it.hasNext()) {
            CacheLevel<K, V> cacheLevel = it.next();
            if (cacheLevel.containsKey(key)) {
                return Optional.ofNullable(cacheLevel.remove(key).get().getValue());
            }
        }
        return Optional.empty();
    }

    public int getLevelQuantity() {
        return cacheList.size();
    }
}
