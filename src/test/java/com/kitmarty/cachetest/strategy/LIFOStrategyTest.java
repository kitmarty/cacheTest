package com.kitmarty.cachetest.strategy;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LIFOStrategyTest {
    private Strategy<Integer> strategy = new LIFOStrategy<>(3);

    @Test
    public void testLIFOReplaceKeyWhenCacheIsNotFull(){
        strategy.put(1);
        strategy.put(1);
        assertThat(strategy.getSize(),is(1));
    }

    @Test
    public void testLIFOMaxSize(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        strategy.put(5);
        assertThat(strategy.getSize(),is(3));
    }

    @Test
    public void testLIFOStrategy1(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4).get(),is(3));
    }

    @Test
    public void testLIFOStrategy2(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5).get(),is(4));
    }

    @Test
    public void testLIFOUpdate(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4).get(),is(3));
    }

    @Test
    public void testLIFOContainsKey(){
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.containsKey(2),is(true));
    }
}
