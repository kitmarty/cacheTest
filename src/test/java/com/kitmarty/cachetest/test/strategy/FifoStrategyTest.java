package com.kitmarty.cachetest.test.strategy;

import com.kitmarty.cachetest.strategy.FifoStrategy;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class FifoStrategyTest {

    private final Strategy<Integer> strategy = new FifoStrategy<>(3);

    @Test
    public void FifoReplaceKeyWhenCacheIsNotFull() {
        strategy.put(1);
        strategy.put(1);
        assertThat(strategy.getSize(), is(1));
    }

    @Test
    public void FifoMaxSize() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        strategy.put(5);
        assertThat(strategy.getSize(), is(3));
    }

    @Test
    public void FifoStrategyFullCacheDisplaceFirstElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4).get(), is(1));
    }

    @Test
    public void FifoStrategyFullCacheDisplaceSecondElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5).get(), is(2));
    }

    @Test
    public void FifoUpdateFirstAndDisplaceFirst() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4).get(), is(1));
    }

    @Test
    public void FifoUpdateNonExisting() {
        assertThat(strategy.update(1), is(false));
    }

    @Test
    public void FifoContainsKey() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.containsKey(1), is(true));
    }

    @Test(expected = NullPointerException.class)
    public void FifoNullKey() {
        strategy.put(null);
    }

    @Test
    public void FifoStaticInit() {
        Strategy.createFifo(5);
    }

    @Test
    public void FifoRemove() {
        strategy.put(1);
        strategy.put(2);
        strategy.remove(1);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5).get(), is(2));
    }
}
