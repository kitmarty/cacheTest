package com.kitmarty.cachetest.strategy;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class LRUStrategyTest {
    private Strategy<Integer> strategy = new LRUStrategy<>(3);

    @Test
    public void testLRUReplaceKeyWhenCacheIsNotFull(){
        strategy.put(1);
        strategy.put(1);
        assertThat(strategy.getSize(),is(1));
    }

    @Test
    public void testLRUMaxSize(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        strategy.put(5);
        assertThat(strategy.getSize(),is(3));
    }

    @Test
    public void testLRUStrategy1(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4).get(),is(1));
    }

    @Test
    public void testLRUStrategy2(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5).get(),is(2));
    }

    @Test
    public void testLRUUpdateExist(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4).get(),is(2));
    }

    @Test
    public void testLRUUpdateNotExist(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(0);
        assertThat(strategy.put(4).get(),is(1));
    }

    @Test
    public void testLRUContainsKey(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.containsKey(2),is(true));
    }

    @Test
    public void testLRUParentClassgetCapacity(){
        assertThat(strategy.getCapacity(),is(3));
    }
}
