package com.kitmarty.cachetest.cache;

import java.util.*;

public class MultiLevelCache<K, V> implements Cache<K, V> {

    private final LinkedList<CacheLevel<K, V>> cacheList = new LinkedList<>();

    public MultiLevelCache(CacheLevel<K, V>... cacheLevels) {
        Collections.addAll(cacheList, cacheLevels);
        if (cacheList.size() == 0) {
            throw new IllegalArgumentException("MultiLevelCache must have one level at least");
        }
    }

    public MultiLevelCache(LinkedList<CacheLevel<K, V>> cacheList) {
        this.cacheList.addAll(cacheList);
    }

    public void addLevel(CacheLevel<K, V> cacheLevel) {
        cacheList.add(cacheLevel);
    }

    @Override
    public Optional<Map.Entry<K, V>> put(K key, V value) {
        remove(key);
        Iterator<CacheLevel<K, V>> it = cacheList.iterator();
        while (it.hasNext()) {
            Optional<Map.Entry<K, V>> displacedEntry = it.next().put(key, value);
            if (!displacedEntry.isPresent()) {
                return Optional.empty();
            }
            key = displacedEntry.get().getKey();
            value = displacedEntry.get().getValue();
        }
        return Optional.of(new AbstractMap.SimpleEntry<>(key, value));
    }

    @Override
    public Optional<V> get(K key) {
        Optional<V> removedValue = remove(key);
        if (removedValue.isPresent()) {
            put(key, removedValue.get());
            return removedValue;
        }
        return Optional.empty();
    }

    @Override
    public boolean containsKey(K key) {
        Iterator<CacheLevel<K, V>> it = cacheList.iterator();
        while (it.hasNext()) {
            if (it.next().containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getSize() {
        int counter = 0;
        Iterator<CacheLevel<K, V>> it = cacheList.iterator();
        while (it.hasNext()) {
            counter += it.next().getSize();
        }
        return counter;
    }

    private Optional<V> remove(K key) {
        Iterator<CacheLevel<K, V>> it = cacheList.iterator();
        while (it.hasNext()) {
            CacheLevel<K, V> cacheLevel = it.next();
            Optional<Map.Entry<K, V>> removedEntry = cacheLevel.remove(key);
            if (removedEntry.isPresent()) {
                return Optional.ofNullable(removedEntry.get().getValue());
            }
        }
        return Optional.empty();
    }

    /**
     * @return Quantity of cache levels
     */
    public int getLevelQuantity() {
        return cacheList.size();
    }

    public int getCapacity() {
        int counter = 0;
        Iterator<CacheLevel<K, V>> it = cacheList.iterator();
        while (it.hasNext()) {
            counter += it.next().getCapacity();
        }
        return counter;
    }
}