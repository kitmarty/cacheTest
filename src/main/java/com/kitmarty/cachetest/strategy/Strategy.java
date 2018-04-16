package com.kitmarty.cachetest.strategy;

import java.util.Optional;

/**
 * An interface describes common methods of caching strategy.
 */
public abstract class Strategy<K> {

    protected final int size;

    public Strategy(int size) {
        this.size = size;
    }

    public abstract Optional<K> put(K key);

    /**
     * This method will be used for updating the position of key in the cache depending on strategy.
     * For example, if we use simple strategy fifo/lifo, get method won't affect to the cache.
     * If we apply this method for lru strategy, it has to make some changes in ordering.
     * @param key
     */
    public abstract boolean update(K key);

    /**
     * In comparison with get method, this method check if the key exists in the cache and return true/false.
     * This method doesn't make any changes in cache ordering.
     * @param key
     * @return
     */
    public abstract boolean containsKey(K key);

    /**
     * @return Maximum quantity of cache elements
     */
    public int getCapacity(){
        return size;
    }

    /**
     *
     * @return Current quantity of cache elements
     */
    public abstract int getSize();
}
