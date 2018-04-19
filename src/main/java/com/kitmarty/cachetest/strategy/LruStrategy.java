package com.kitmarty.cachetest.strategy;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * This class implements least recently used caching strategy.
 *
 * @param <K> key value
 */
public class LruStrategy<K> extends AbstractStrategy<K>
        implements Strategy<K> {
    private final LinkedHashMap<K, Object> queue;

    public LruStrategy(int size) {
        super(size);
        queue = new LinkedHashMap<K, Object>(size, 0.75f, true) {
            private final int MAX_ENTRIES = size;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, Object> eldest) {
                return size() > MAX_ENTRIES;
            }
        };
    }

    @Override
    public Optional<K> put(K key) {
        Objects.requireNonNull(key, "LruStrategy put null key");
        Optional<K> valueToReturn;
        if ((queue.size() == size) && (!containsKey(key))) {
            valueToReturn = Optional.of(queue.entrySet().iterator().next().getKey());
        } else {
            valueToReturn = Optional.empty();
        }
        queue.put(key, new Object());
        return valueToReturn;
    }

    @Override
    public boolean update(K key) {
        return !(queue.get(key) == null);
    }

    @Override
    public boolean containsKey(K key) {
        return queue.containsKey(key);
    }

    @Override
    public int getSize() {
        return queue.size();
    }

    public void remove(K key) {
        queue.remove(key);
    }
}
