package com.kitmarty.cachetest.test.cache;

import com.kitmarty.cachetest.cache.CacheLevel;
import com.kitmarty.cachetest.storage.MemoryStorage;
import com.kitmarty.cachetest.strategy.StrategyCreator;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CacheLevelTest {
    private final CacheLevel<Integer, String> cacheLevel = new CacheLevel<>(MemoryStorage.ofHashMap(), StrategyCreator.createLru(3));

    @Test(expected = NullPointerException.class)
    public void initNullStorage() {
        new CacheLevel<>(null, StrategyCreator.createFifo(5));
    }

    @Test(expected = NullPointerException.class)
    public void initNullStrategy() {
        new CacheLevel<>(MemoryStorage.ofHashMap(), null);
    }

    @Test
    public void putSameKey() {
        cacheLevel.put(1, "One");
        cacheLevel.put(1, "One new");
        assertThat(cacheLevel.get(1), is(Optional.of("One new")));
    }

    @Test
    public void sizeIsLowerThanLimitAndEmptyReturnAfterPut() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        assertThat(cacheLevel.put(3, "Three"), is(Optional.empty()));
    }

    @Test
    public void sizeEqualsLimitAndReturnDisplacedEntryAfterPut() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.put(4, "Four").isPresent(), is(true));
    }

    @Test
    public void getExistingElement() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.get(1), is(Optional.of("One")));
    }

    @Test
    public void getNonExistingElement() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.get(4).isPresent(), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void putNullKey() {
        cacheLevel.put(null, "Null");
    }

    @Test(expected = NullPointerException.class)
    public void putNullValue() {
        cacheLevel.put(0, null);
    }

    @Test
    public void getValueByNullKey() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.get(null).isPresent(), is(false));
    }

    @Test
    public void cacheContainsKey() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.containsKey(2), is(true));
    }

    @Test
    public void keyRemove() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.remove(1), is(Optional.of(new AbstractMap.SimpleEntry<>(1,"One"))));
    }

    @Test
    public void removeNonExistingElement() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.remove(4).isPresent(), is(false));
    }

    @Test
    public void compareCapacityVsSize(){
        assertThat(cacheLevel.getSize(),is(0));
        assertThat(cacheLevel.getCapacity(),is(3));
        cacheLevel.put(1, "One");
        assertThat(cacheLevel.getSize(),is(1));
        assertThat(cacheLevel.getCapacity(),is(3));
    }
}