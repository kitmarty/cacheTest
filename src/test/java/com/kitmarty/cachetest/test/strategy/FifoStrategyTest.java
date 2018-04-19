package com.kitmarty.cachetest.test.strategy;

import com.kitmarty.cachetest.strategy.FifoStrategy;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class FifoStrategyTest {

    private final Strategy<Integer> strategy = new FifoStrategy<>(3);

    @Test
    public void replaceKeyWhenCacheIsNotFull() {
        strategy.put(1);
        strategy.put(1);
        assertThat(strategy.getSize(), is(1));
    }

    @Test
    public void maxSizeIsLimited() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        strategy.put(5);
        assertThat(strategy.getSize(), is(3));
    }

    @Test
    public void fullCacheAndDisplaceFirstElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4), is(Optional.of(1)));
    }

    @Test
    public void updateFirstElementAndThenDisplaceIt() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4), is(Optional.of(1)));
    }

    @Test
    public void updateNonExistingKey() {
        assertThat(strategy.update(1), is(false));
    }

    @Test
    public void cacheContainsKey() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.containsKey(1), is(true));
    }

    @Test(expected = NullPointerException.class)
    public void putNullKey() {
        strategy.put(null);
    }

    @Test
    public void keyRemove() {
        strategy.put(1);
        strategy.put(2);
        strategy.remove(1);
        assertThat(strategy.getSize(),is(1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void strategyInitWithoutElements(){
        new FifoStrategy<>(0);
    }
}
