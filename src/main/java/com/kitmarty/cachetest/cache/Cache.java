package com.kitmarty.cachetest.cache;

import java.util.Map;
import java.util.Optional;

/**
 * An object that allows to manage the cache moving objects between memory, disk etc.
 * Works with pair Key-Value.
 * Key cannot be null or duplicates through the cache.
 * Values cannot be null.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values contained in this cache
 */
public interface Cache<K, V> {
    /**
     * Adds new pair Key-Value to the cache.
     * If the cache already contains value with particular key, the key will be replaced.
     *
     * @param key the key of an object
     *            //TODO change throws to specific cache exception
     * @return displaced value if the cache is full. If there is no displaced value
     * @throws NullPointerException if the key is null
     */
    Optional<Map.Entry<K, V>> put(K key, V value); //throws CacheException;

    /**
     * Returns the value from cache by the key.
     * If the key doesn't exist in the cache null will be returned
     *
     * @param key the key of an object
     * @return the value to which the specified key is mapped
     * @throws NullPointerException if the key is null
     */
    Optional<V> get(K key);

    /**
     * Check whether the cache contains particular Key
     *
     * @param key the key of an object
     * @return true if cache contains the key, false if not
     * @throws NullPointerException if the key is null
     */
    boolean containsKey(K key);
}