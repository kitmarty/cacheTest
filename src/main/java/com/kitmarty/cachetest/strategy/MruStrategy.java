package com.kitmarty.cachetest.strategy;

/**
 * This class implements most recently use strategy.
 * It extends Lifo strategy because they differ from each other with update method only
 */
public class MruStrategy<K> extends LifoStrategy<K>
        implements Strategy<K> {

    public MruStrategy(int size) {
        super(size);
    }

    @Override
    public boolean update(K key) {
        if (queue.removeFirstOccurrence(key)) {
            queue.push(key);
            return true;
        }
        return false;
    }
}
