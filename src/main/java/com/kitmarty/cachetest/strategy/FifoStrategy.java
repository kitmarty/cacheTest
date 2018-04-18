package com.kitmarty.cachetest.strategy;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Optional;

/**
 * FifoStrategy class extends Storage abstract class and implements simple createFifo strategy of queue.
 * This simple strategy allows add new elements to the queue,
 * but getting elements doesn't have any affect for elements order in the queue.
 * If you try to add new element, but it's already in queue, this element will be moved to the end of the queue.
 *
 * @param <K> type of key
 */
public class FifoStrategy<K> extends Strategy<K> {

    private final ArrayDeque<K> queue = new ArrayDeque<>();

    public FifoStrategy(int size) {
        super(size);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<K> put(K key) {
        Objects.requireNonNull(key, "FifoStrategy put null key");
        Optional<K> valueToReturn;
        queue.removeFirstOccurrence(key);
        if (queue.size() == size) {
            valueToReturn = Optional.ofNullable(queue.remove());
        } else {
            valueToReturn = Optional.empty();
        }
        queue.add(key);
        return valueToReturn;
    }

    @Override
    public boolean update(K key) {
        return containsKey(key);
    }

    @Override
    public boolean containsKey(K key) {
        return queue.contains(key);
    }

    @Override
    public int getSize() {
        return queue.size();
    }

    @Override
    public void remove(K key) {
        queue.remove(key);
    }
}
