package cachetest;

/**
 * An object that allows to manage the cache moving objects between memory, disk etc.
 * Works with pair Key-Value.
 * Key cannot be null or duplicates through the cache.
 * @param <K> the type of keys the cache uses
 * @param <V> the type of values the cache contains
 */
public interface Cache<K,V> {
    /**
     * Adds new pair Key-Value to the cache.
     * If the cache already contains value with particular key, the key will be replaced.
     * @param key the key of an object
     * @throws ClassCastException if the key or the value are of an inappropriate type for this cache
     * @throws NullPointerException if the key is null
     */
    void set(K key, V value);

    /**
     * Returns the value from cache by the key.
     * If the key doesn't exist in the cache null will be returned
     * @param key the key of an object
     * @throws ClassCastException if the key is of an inappropriate type for this cache
     * @throws NullPointerException if the key is null
     * @return the value to which the specified key is mapped
     */
    V get(K key);

    /**
     * Clears the cache.
     */
    void clear();

    /**
     * Removes the value from cache associated with the key
     * @throws ClassCastException if the key is of an inappropriate type for this cache
     * @throws NullPointerException if the key is null
     */
    void remove(K key);

    /**
     * Check whether the cache contains particular Key
     * @param key the key of an object
     * @throws ClassCastException if the key is of an inappropriate type for this cache
     * @throws NullPointerException if the key is null
     * @return true if cache contains the key, false if not
     */
    boolean containsKey(K key);
}