package cachetest;

public interface Cache<K,V> {
    void set(K key, V value);

    //TODO javadoc for each method
    //TODO exceptions, what to throw
    //TODO
    /**
     *
     * @param key
     * @return
     */
    V get(K key);

    void clear();

    void remove(K key);
}