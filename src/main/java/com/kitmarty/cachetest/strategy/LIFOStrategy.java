package com.kitmarty.cachetest.strategy;

import java.util.ArrayDeque;
import java.util.Optional;

/**
 * LIFOStrategy class extends Storage abstract class and implements simple lifo strategy of stack.
 * This simple strategy allows add new elements to the stack,
 * but getting elements doesn't have any affect for elements order in the stack.
 * If you try to add new element, but it's already in queue, this element will be moved to the top of the stack.
 * @param <K> type of key
 */
public class LIFOStrategy<K> extends Strategy<K> {

    private final ArrayDeque<K> queue = new ArrayDeque<K>();

    public LIFOStrategy(int size) {
        super(size);
    }

    @Override
    public Optional<K> put(K key) {
        Optional<K> valueToReturn;
        queue.removeFirstOccurrence(key);
        if (queue.size()==size){
            valueToReturn = Optional.ofNullable(queue.pop());
        }else{
            valueToReturn = Optional.empty();
        }
        queue.push(key);
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
