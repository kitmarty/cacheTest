package com.kitmarty.cachetest.test.strategy;

import com.kitmarty.cachetest.strategy.LifoStrategy;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class LifoStrategyTest {

    private final Strategy<Integer> strategy = new LifoStrategy<>(3);

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
    public void fullCacheAndDisplaceTopStackElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4), is(Optional.of(3)));
    }

    @Test
    public void fullCacheAndDisplaceTopStackElementTwice() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5), is(Optional.of(4)));
    }

    @Test
    public void updateFirstElementAndThenDisplaceIt() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4), is(Optional.of(3)));
    }

    @Test
    public void cacheContainsKey() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.containsKey(2), is(true));
    }

    @Test
    public void updateNonExistingKey() {
        assertThat(strategy.update(1), is(false));
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
