package com.kitmarty.cachetest.strategy;

import java.util.Optional;

/**
 * An interface describes common methods of caching strategy.
 */
public interface Strategy<K> {

    /**
     * This method adds key elements to queue,stack etc depending on strategy implementations.
     *
     * @param key element for adding
     * @return displaced key if the cache is full
     */
    Optional<K> put(K key);

    /**
     * This method will be used for updating the position of key in the cache depending on strategy.
     * For example, if we use simple strategy createFifo/createLifo, get method won't affect to the cache.
     * If we apply this method for createLru strategy, it has to make some changes in ordering.
     *
     * @param key key whose position will be updated
     * @return true if the key is presented in the cache else return false
     */
    boolean update(K key);

    /**
     * Removes key from strategy queue
     *
     * @param key removing key
     */
    void remove(K key);

    /**
     * In comparison with get method, this method check if the key exists in the cache and return true/false.
     * This method doesn't make any changes in cache ordering.
     *
     * @param key key for check
     * @return true if strategy queue contains key
     */
    boolean containsKey(K key);

    /**
     * @return Maximum quantity of cache elements
     */
    int getCapacity();

    /**
     * @return Current quantity of cache elements
     */
    int getSize();
}
