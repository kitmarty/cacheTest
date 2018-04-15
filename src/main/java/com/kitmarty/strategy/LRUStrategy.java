package com.kitmarty.strategy;

import java.util.ArrayDeque;
import java.util.Optional;

/**
 * This class implements least recently used caching strategy.
 * @param <K> key value
 */
public class LRUStrategy<K> extends Strategy<K> {

    private final ArrayDeque<K> queue = new ArrayDeque<>();

    public LRUStrategy(int size) {
        super(size);
    }

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
        if (queue.removeFirstOccurrence(key)) {
            queue.add(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return queue.contains(key);
    }

    @Override
    public String toString() {
        return "LRUStrategy{" +
                "queue=" + queue +
                '}';
    }

    public static void main(String[] args) {
        Strategy<Integer> strat = new LRUStrategy<Integer>(3);
        strat.put(1);
        strat.put(2);
        strat.put(3);
        strat.update(1);
        strat.put(4);
        System.out.print(strat.toString());
    }

}
