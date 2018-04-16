package com.kitmarty.cachetest.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapQueue<K,V> extends LinkedHashMap<K,V> {
    private final int MAX_ENTRIES;

    public LinkedHashMapQueue(int initialCapacity, float loadFactor, boolean accessOrder, int size) {
        super(initialCapacity, loadFactor, accessOrder);
        MAX_ENTRIES = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_ENTRIES;
    }
}