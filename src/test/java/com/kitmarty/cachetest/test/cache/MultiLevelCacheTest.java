package com.kitmarty.cachetest.test.cache;

import com.kitmarty.cachetest.cache.CacheLevel;
import com.kitmarty.cachetest.cache.MultiLevelCache;
import com.kitmarty.cachetest.storage.MemoryStorage;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import java.util.LinkedHashSet;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MultiLevelCacheTest {
    private MultiLevelCache<Integer, String> multiLevelCache = new MultiLevelCache<>();

    @Test
    public void MultiLevelAddLevel() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(5)));
        assertThat(multiLevelCache.getLevelQuantity(), is(2));
    }

    @Test
    public void MultiLevelInitWithList() {
        LinkedHashSet<CacheLevel<Integer, String>> cacheList = new LinkedHashSet<>();
        cacheList.add(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        cacheList.add(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(5)));
        MultiLevelCache<Integer, String> multiLevelCache1 = new MultiLevelCache<>(cacheList);
        assertThat(multiLevelCache1.getLevelQuantity(), is(2));
    }

    @Test(expected = NullPointerException.class)
    public void MultiLevelWithoutLevel() {
        multiLevelCache.put(1, "One");
    }

    @Test
    public void MultiLevelTestSimplePutThreeLevel() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(2)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lifo(3)));
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        multiLevelCache.put(5, "Five");
        multiLevelCache.put(6, "Six");
        multiLevelCache.put(7, "Seven");
        assertThat(multiLevelCache.put(8, "Eight").get().getValue(), is("Three"));
    }

    @Test
    public void MultiLevelGetFromThirdLevelAndAutoInsertInFirstCacheLevel() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(2)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lifo(3)));
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        multiLevelCache.put(5, "Five");
        multiLevelCache.put(6, "Six");
        multiLevelCache.put(7, "Seven");
        multiLevelCache.get(1);
        assertThat(multiLevelCache.put(8, "Eight").get().getValue(), is("Four"));
    }

    @Test
    public void MultiLevelSimpleGetReturn() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(2)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lifo(3)));
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        multiLevelCache.put(5, "Five");
        multiLevelCache.put(6, "Six");
        multiLevelCache.put(7, "Seven");
        assertThat(multiLevelCache.get(1).get(), is("One"));
    }

    @Test
    public void MultiLevelGetNullKey() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(2)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lifo(3)));
        multiLevelCache.put(1, "One");
        assertThat(multiLevelCache.get(2).isPresent(), is(false));
    }

    @Test
    public void MultiLevelContainsExistingKey() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(2)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lifo(3)));
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        multiLevelCache.put(5, "Five");
        multiLevelCache.put(6, "Six");
        multiLevelCache.put(7, "Seven");
        assertThat(multiLevelCache.containsKey(1), is(true));
    }

    @Test
    public void MultiLevelContainsNonExistingKey() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(2)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lifo(3)));
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        multiLevelCache.put(5, "Five");
        multiLevelCache.put(6, "Six");
        multiLevelCache.put(7, "Seven");
        assertThat(multiLevelCache.containsKey(8), is(false));
    }

    @Test
    public void MultiLevelRemoveNonExistingKey() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(2)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lifo(3)));
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        multiLevelCache.put(5, "Five");
        multiLevelCache.put(6, "Six");
        multiLevelCache.put(7, "Seven");
    }
}