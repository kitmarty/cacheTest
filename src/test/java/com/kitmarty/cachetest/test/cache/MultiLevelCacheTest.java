package com.kitmarty.cachetest.test.cache;

import com.kitmarty.cachetest.cache.CacheLevel;
import com.kitmarty.cachetest.cache.MultiLevelCache;
import com.kitmarty.cachetest.storage.MemoryStorage;
import com.kitmarty.cachetest.strategy.StrategyCreator;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MultiLevelCacheTest {
    private final MultiLevelCache<Integer, String> multiLevelCache = new MultiLevelCache<>(
            new CacheLevel<>(MemoryStorage.ofHashMap(), StrategyCreator.createLru(3)),
            new CacheLevel<>(MemoryStorage.ofHashMap(), StrategyCreator.createLifo(2))
    );

    @Test
    public void addLevel() {
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), StrategyCreator.createLru(3)));
        multiLevelCache.addLevel(new CacheLevel<>(MemoryStorage.ofHashMap(), StrategyCreator.createFifo(5)));
        assertThat(multiLevelCache.getLevelQuantity(), is(4));
    }

    @Test
    public void initWithList() {
        LinkedList<CacheLevel<Integer, String>> cacheList = new LinkedList<>();
        cacheList.add(new CacheLevel<>(MemoryStorage.ofHashMap(), StrategyCreator.createLru(3)));
        cacheList.add(new CacheLevel<>(MemoryStorage.ofHashMap(), StrategyCreator.createFifo(5)));
        MultiLevelCache<Integer, String> multiLevelCache1 = new MultiLevelCache<>(cacheList);
        assertThat(multiLevelCache1.getLevelQuantity(), is(2));
    }

    @Test
    public void maxSizeIsLimited() {
        assertThat(multiLevelCache.getSize(), is(0));
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        //< 4 3 2 > - < 1 . >
        assertThat(multiLevelCache.getSize(), is(4));
        multiLevelCache.put(5, "Five");
        //< 5 4 3 > - < 2 1 >
        assertThat(multiLevelCache.getSize(), is(5));
        multiLevelCache.put(6, "Six");
        multiLevelCache.put(7, "Seven");
        //< 5 4 3 > - < 2 1 >
        assertThat(multiLevelCache.getSize(), is(5));
    }

    @Test
    public void getElementAndMoveItToFirstPosition() {
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        //< 3 2 1 > - < . . >
        multiLevelCache.put(4, "Four");
        //< 4 3 2 > - < 1 . >
        multiLevelCache.put(5, "Five");
        //< 5 4 3 > - < 2 1 >
        multiLevelCache.get(1);
        //< 1 5 4 > - < 3 2 >
        assertThat(multiLevelCache.put(6, "Six"), is(Optional.of(new AbstractMap.SimpleEntry<>(3,"Three"))));
        //< 6 1 5 > - < 4 2 >
    }

    @Test
    public void getExistingElement() {
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        multiLevelCache.put(5, "Five");
        //< 5 4 3 > - < 2 1 >
        assertThat(multiLevelCache.get(1), is(Optional.of("One")));
    }

    @Test
    public void getNonExistingKey() {
        assertThat(multiLevelCache.get(1).isPresent(), is(false));
    }

    @Test
    public void cacheContainsKey() {
        multiLevelCache.put(1, "One");
        multiLevelCache.put(2, "Two");
        multiLevelCache.put(3, "Three");
        multiLevelCache.put(4, "Four");
        //< 4 3 2 > - < 1 . >
        assertThat(multiLevelCache.containsKey(1), is(true));
        assertThat(multiLevelCache.containsKey(5), is(false));
    }

    @Test (expected = NullPointerException.class)
    public void putNullKey(){
        multiLevelCache.put(null, "One");
    }

    @Test (expected = NullPointerException.class)
    public void putNullValue(){
        multiLevelCache.put(1, null);
    }

    @Test
    public void compareCapacityVsSize(){
        assertThat(multiLevelCache.getSize(),is(0));
        assertThat(multiLevelCache.getCapacity(),is(5));
        multiLevelCache.put(1, "One");
        assertThat(multiLevelCache.getSize(),is(1));
        assertThat(multiLevelCache.getCapacity(),is(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void emptyInit(){
        MultiLevelCache<Integer,String> a = new MultiLevelCache<>();
    }
}