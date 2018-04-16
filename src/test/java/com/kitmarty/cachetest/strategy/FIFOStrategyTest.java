package com.kitmarty.cachetest.strategy;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class FIFOStrategyTest {
    private Strategy<Integer> strategy = new FIFOStrategy<>(3);

    @Test
    public void testFIFOReplaceKeyWhenCacheIsNotFull(){
        strategy.put(1);
        strategy.put(1);
        assertThat(strategy.getSize(),is(1));
    }

    @Test
    public void testFIFOMaxSize(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        strategy.put(5);
        assertThat(strategy.getSize(),is(3));
    }

    @Test
    public void testFIFOStrategy1(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4).get(),is(1));
    }

    @Test
    public void testFIFOStrategy2(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5).get(),is(2));
    }

    @Test
    public void testFIFOUpdate(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4).get(),is(1));
    }

    @Test
    public void testFIFOContainsKey(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.containsKey(1),is(true));
    }
}
