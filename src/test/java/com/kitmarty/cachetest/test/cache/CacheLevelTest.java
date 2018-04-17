package com.kitmarty.cachetest.test.cache;

import com.kitmarty.cachetest.cache.CacheLevel;
import com.kitmarty.cachetest.storage.MemoryStorage;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class CacheLevelTest {
    private CacheLevel<Integer,String> cacheLevel = new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Lru(3));

    @Test
    public void CacheLevelInit(){
        new CacheLevel<>(MemoryStorage.ofHashMap(), Strategy.Fifo(5));
    }


    @Test (expected = NullPointerException.class)
    public void CacheLevelInitNullStorage() {
        new CacheLevel<>(null, Strategy.Fifo(5));
    }

    @Test (expected = NullPointerException.class)
    public void CacheLevelInitNullStrategy() {
        new CacheLevel<>(MemoryStorage.ofHashMap(), null);
    }

    @Test
    public void CacheLevelPutSameKey() {
        cacheLevel.put(1,"One");
        cacheLevel.put(1,"One new");
        assertThat(cacheLevel.get(1).get(),is("One new"));
    }

    @Test
    public void CacheLevelSizeEmptyReturn() {
        cacheLevel.put(1,"One");
        cacheLevel.put(2,"Two");
        assertThat(cacheLevel.put(3,"Three"),is(Optional.empty()));
    }

    @Test
    public void CacheLevelSizeFirstEntryReturn() {
        cacheLevel.put(1,"One");
        cacheLevel.put(2,"Two");
        cacheLevel.put(3,"Three");
        assertThat(cacheLevel.put(4,"Four").get().getValue(),is("One"));
    }

    @Test
    public void CacheLevelGetExistingElement() {
        cacheLevel.put(1,"One");
        cacheLevel.put(2,"Two");
        cacheLevel.put(3,"Three");
        assertThat(cacheLevel.get(1).get(),is("One"));
    }

    @Test
    public void CacheLevelGetNonExistingElement() {
        cacheLevel.put(1,"One");
        cacheLevel.put(2,"Two");
        cacheLevel.put(3,"Three");
        assertThat(cacheLevel.get(4).isPresent(),is(false));
    }

    @Test (expected = NullPointerException.class)
    public void CacheLevelPutNullKey() {
        cacheLevel.put(null,"Null");
    }

    @Test (expected = NullPointerException.class)
    public void CacheLevelPutNullValue() {
        cacheLevel.put(0,null);
    }

    @Test
    public void CacheLevelGetValueByNullKey() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.get(null).isPresent(),is(false));
    }

    @Test
    public void CacheLevelContainsKey() {
        cacheLevel.put(1, "One");
        cacheLevel.put(2, "Two");
        cacheLevel.put(3, "Three");
        assertThat(cacheLevel.containsKey(2),is(true));
    }
}