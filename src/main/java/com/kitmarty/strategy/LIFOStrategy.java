package com.kitmarty.strategy;

import com.kitmarty.strategy.Strategy;

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

    private final ArrayDeque<K> stack = new ArrayDeque<>();

    public LIFOStrategy(int size) {
        super(size);
    }

    @Override
    public Optional<K> put(K key) {
        Optional<K> valueToReturn;
        stack.removeFirstOccurrence(key);
        if (stack.size()==size){
            valueToReturn = Optional.ofNullable(stack.pop());
        }else{
            valueToReturn = Optional.empty();
        }
        stack.push(key);
        return valueToReturn;
    }

    @Override
    public boolean update(K key) {
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return stack.contains(key);
    }

    @Override
    public String toString() {
        return "LIFOStrategy{" +
                "stack=" + stack +
                '}';
    }

    public static void main(String[] args) {
        Strategy<Integer> strat = new LIFOStrategy<Integer>(3);
        strat.put(1);
        strat.put(1);
        strat.put(2);
        strat.put(3);
        strat.put(1);
        System.out.print(strat.toString());
    }
}
