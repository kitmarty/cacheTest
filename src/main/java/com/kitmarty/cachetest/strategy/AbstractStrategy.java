package com.kitmarty.cachetest.strategy;

public abstract class AbstractStrategy<K> implements Strategy<K> {

    final int size;

    AbstractStrategy(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Strategy must have 1 or more elements");
        }
        this.size = size;
    }

    public int getCapacity() {
        return size;
    }
}