package com.kitmarty.cachetest.strategy;

public final class StrategyCreator {

    public static <K> Strategy<K> createFifo(int size) {
        return new FifoStrategy<>(size);
    }

    public static <K> Strategy<K> createLifo(int size) {
        return new LifoStrategy<>(size);
    }

    public static <K> Strategy<K> createLru(int size) {
        return new LruStrategy<>(size);
    }

    private StrategyCreator() {
    }
}
