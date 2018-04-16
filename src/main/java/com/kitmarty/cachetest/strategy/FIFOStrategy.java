package com.kitmarty.cachetest.strategy;

import java.util.ArrayDeque;
import java.util.Optional;

/**
 * FIFOStrategy class extends Storage abstract class and implements simple fifo strategy of queue.
 * This simple strategy allows add new elements to the queue,
 * but getting elements doesn't have any affect for elements order in the queue.
 * If you try to add new element, but it's already in queue, this element will be moved to the end of the queue.
 * @param <K> type of key
 */
public class FIFOStrategy<K> extends Strategy<K> {

    private final ArrayDeque<K> queue = new ArrayDeque<K>();

    public FIFOStrategy(int size) {
        super(size);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Optional<K> put(K key) {
        Optional<K> valueToReturn;
        queue.removeFirstOccurrence(key);
        if (queue.size()==size){
            valueToReturn = Optional.ofNullable(queue.remove());
        }else{
            valueToReturn = Optional.empty();
        }
        queue.add(key);
        return valueToReturn;
    }

    @Override
    public boolean update(K key) {
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return queue.contains(key);
    }

    @Override
    public int getSize() {
        return queue.size();
    }
}
