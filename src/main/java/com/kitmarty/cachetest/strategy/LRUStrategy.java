package com.kitmarty.cachetest.strategy;

import com.kitmarty.cachetest.service.LinkedHashMapQueue;

import java.util.Optional;

/**
 * This class implements least recently used caching strategy.
 * @param <K> key value
 */
public class LRUStrategy<K> extends Strategy<K> {

    private final LinkedHashMapQueue<K,K> queue;

    public LRUStrategy(int size) {
        super(size);
        queue = new LinkedHashMapQueue<K,K>(size,0.75f,true, size);
    }

    @Override
    public Optional<K> put(K key) {
        Optional<K> valueToReturn;
        valueToReturn = Optional.ofNullable(queue.put(key, key));
        return valueToReturn;
    }

    @Override
    public boolean update(K key) {
        if (!(queue.get(key)==null)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return queue.containsKey(key);
    }

    @Override
    public int getSize() {
        return queue.size();
    }
}
