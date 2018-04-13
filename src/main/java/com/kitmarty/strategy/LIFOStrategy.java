package com.kitmarty.strategy;

import com.kitmarty.strategy.Strategy;

import java.util.ArrayDeque;
import java.util.Optional;

public class LIFOStrategy<K> extends Strategy<K> {

    private final ArrayDeque<K> stack = new ArrayDeque<>();

    public LIFOStrategy(int size) {
        super(size);
    }

    @Override
    public Optional<K> put(K key) {
        Optional<K> valueToReturn;
        if (stack.size()==size){
            valueToReturn = Optional.ofNullable(stack.pop());
        }else{
            valueToReturn = Optional.empty();
        }
        stack.push(key);
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
