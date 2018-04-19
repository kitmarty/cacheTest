package com.kitmarty.cachetest.test.strategy;

import com.kitmarty.cachetest.strategy.LruStrategy;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class LruStrategyTest {

    private final Strategy<Integer> strategy = new LruStrategy<>(3);

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
    public void updateExistingElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        //< 3 2 1 >
        strategy.update(1);
        //< 1 3 2 >
        assertThat(strategy.put(4), is(Optional.of(2)));
    }

    @Test
    public void updateNonExistingElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        //< 3 2 1 >
        strategy.update(0);
        //< 3 2 1 >
        assertThat(strategy.put(4), is(Optional.of(1)));
    }

    @Test
    public void cacheContainsKey() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.containsKey(2), is(true));
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
        assertThat(strategy.getSize(), is(1));
    }
}
