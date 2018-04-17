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

    public static <K> Strategy<K> Fifo(int size) {
        return new FifoStrategy<>(size);
    }

    public static <K> Strategy<K> Lifo(int size) {
        return new LifoStrategy<>(size);
    }

    public static <K> Strategy<K> Lru(int size) {
        return new LruStrategy<>(size);
    }

    public abstract Optional<K> put(K key);

    /**
     * This method will be used for updating the position of key in the cache depending on strategy.
     * For example, if we use simple strategy Fifo/Lifo, get method won't affect to the cache.
     * If we apply this method for Lru strategy, it has to make some changes in ordering.
     *
     * @return true if the key is presented in the cache else return false
     * @param key
     */
    public abstract boolean update(K key);

    /**
     * In comparison with get method, this method check if the key exists in the cache and return true/false.
     * This method doesn't make any changes in cache ordering.
     *
     * @param key
     * @return
     */
    public abstract boolean containsKey(K key);

    /**
     * @return Maximum quantity of cache elements
     */
    public int getCapacity() {
        return size;
    }

    /**
     * @return Current quantity of cache elements
     */
    public abstract int getSize();

    public abstract void remove(K key);
}
