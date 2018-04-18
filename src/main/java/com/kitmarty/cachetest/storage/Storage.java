package com.kitmarty.cachetest.storage;

import java.util.Optional;

/**
 * An object of abstract storage. It can be implemented as memory storage, disk storage, net storage etc.
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of values contained in this cache
 */
public interface Storage<K, V> {

    /**
     * Puts new key-value pair to the cache.
     * @param key key of pair
     * @param value value of pair
     * @return disposed value, if the cache has no empty cell
     */
    Optional<V> put(K key, V value);

    /**
     * Gets value from cache by key.
     * @param key key for getting value
     * @return value that corresponds to the key
     */
    Optional<V> get(K key);

    /**
     * Removes key-value pair from storage by the key.
     * @param key key for removing value
     * @return value that corresponds to the key
     */
    Optional<V> remove(K key);
}