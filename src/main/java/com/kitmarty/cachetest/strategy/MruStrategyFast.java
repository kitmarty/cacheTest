package com.kitmarty.cachetest.strategy;

import org.apache.commons.collections4.map.LRUMap;

import java.util.Objects;
import java.util.Optional;

/**
 * This class implements MRU strategy using LRUMap from apache collection with some changes in logic.
 *
 * Standard logic provides simple LRU strategy with queue.
 * New elements are adding to the end of the queue.
 * Get method moves element to the start of the queue.
 * If the map is full (reach its size limit) the element from the start of queue will be removed.
 *
 * There is an opportunity to use this class for MRU strategy.
 * removeLRU method has to be overridden for deleting MRU element from the end of the queue.
 * Get method has to move element to the end of the queue.
 * If the map is full (reach its size limit) the element from the end of queue will be removed.
 *
 * @param <K> key value
 */
public class MruStrategyFast<K> extends AbstractStrategy<K>
        implements Strategy<K> {

    private final LRUMap<K, Object> queue;
    private static final Object FAKE_OBJECT = new Object();

    public MruStrategyFast(int size) {
        super(size);
        queue = new LRUMap<K, Object>(size, 0.75f) {
            @Override
            protected boolean removeLRU(LinkEntry<K, Object> entry) {
                this.remove(queue.lastKey());
                return false;
            }
        };
    }

    @Override
    public Optional<K> put(K key) {
        Objects.requireNonNull(key, "LruStrategy put null key");
        Optional<K> valueToReturn = Optional.empty();
        if ((queue.isFull()) && (!queue.containsKey(key))) {
            valueToReturn = Optional.of(queue.lastKey());
        }
        queue.put(key, FAKE_OBJECT);
        return valueToReturn;
    }

    @Override
    public boolean update(K key) {
        return !(queue.get(key, true) == null);
    }

    @Override
    public void remove(K key) {
        queue.remove(key);
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
