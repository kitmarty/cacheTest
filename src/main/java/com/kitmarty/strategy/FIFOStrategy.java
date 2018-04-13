package com.kitmarty.strategy;

import com.kitmarty.strategy.Strategy;

import java.util.ArrayDeque;
import java.util.Optional;

public class FIFOStrategy<K> extends Strategy<K> {

    private final ArrayDeque<K> queue = new ArrayDeque<>();

    public FIFOStrategy(int size) {
        super(size);
    }

    @Override
    public Optional<K> put(K key) {
        Optional<K> valueToReturn;
        if (queue.size()==size){
            valueToReturn = Optional.ofNullable(queue.remove());
        }else{
            valueToReturn = Optional.empty();
        }
        queue.add(key);
        return valueToReturn;
    }

    @Override
    public void update(K key) {

    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }
}
