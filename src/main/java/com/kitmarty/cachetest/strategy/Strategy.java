package com.kitmarty.cachetest.strategy;

import java.util.Optional;

/**
 * An interface describes common methods of caching strategy.
 */
public abstract class Strategy<K> {

    final int size;

    Strategy(int size) {
        this.size = size;
    }

    public static <K> Strategy<K> createFifo(int size) {
        return new FifoStrategy<>(size);
    }

    public static <K> Strategy<K> createLifo(int size) {
        return new LifoStrategy<>(size);
    }

    public static <K> Strategy<K> createLru(int size) {
        return new LruStrategy<>(size);
    }

    public abstract Optional<K> put(K key);

    /**
     * This method will be used for updating the position of key in the cache depending on strategy.
     * For example, if we use simple strategy createFifo/createLifo, get method won't affect to the cache.
     * If we apply this method for createLru strategy, it has to make some changes in ordering.
     *
     * @return true if the key is presented in the cache else return false
     * @param key key whose position will be updated
     */
    public abstract boolean update(K key);

    /**
     * In comparison with get method, this method check if the key exists in the cache and return true/false.
     * This method doesn't make any changes in cache ordering.
     *
     * @param key key for check
     * @return true if strategy queue contains key
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
