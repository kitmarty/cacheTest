package com.kitmarty.cachetest.test.strategy;

import com.kitmarty.cachetest.strategy.LifoStrategy;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class LifoStrategyTest {

    private final Strategy<Integer> strategy = new LifoStrategy<>(3);

    @Test
    public void LifoReplaceKeyWhenCacheIsNotFull() {
        strategy.put(1);
        strategy.put(1);
        assertThat(strategy.getSize(), is(1));
    }

    @Test
    public void LifoMaxSize() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        strategy.put(5);
        assertThat(strategy.getSize(), is(3));
    }

    @Test
    public void LifoStrategyFullCacheDisplaceTopStackElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4).get(), is(3));
    }

    @Test
    public void LifoStrategyFullCacheDisplaceTopStackElementTwice() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5).get(), is(4));
    }

    @Test
    public void LifoUpdate() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4).get(), is(3));
    }

    @Test
    public void LifoContainsKey() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.containsKey(2), is(true));
    }

    @Test
    public void LifoUpdateNonExisting() {
        assertThat(strategy.update(1), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void LifoNullKey() {
        strategy.put(null);
    }

    @Test
    public void LifoStaticInit() {
        Strategy.createLifo(5);
    }

    @Test
    public void LifoRemove() {
        strategy.put(1);
        strategy.put(2);
        strategy.remove(1);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.put(5).get(), is(4));
    }
}
