package com.kitmarty.cachetest.test.strategy;

import com.kitmarty.cachetest.strategy.LruStrategy;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class LruStrategyTest {

    private Strategy<Integer> strategy = new LruStrategy<>(3);

    @Test
    public void LruReplaceKeyWhenCacheIsNotFull() {
        strategy.put(1);
        strategy.put(1);
        assertThat(strategy.getSize(), is(1));
    }

    @Test
    public void LruMaxSize() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        strategy.put(5);
        assertThat(strategy.getSize(), is(3));
    }

    @Test
    public void LruStrategyFullCacheDisplaceFirstElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        assertThat(strategy.put(4).get(), is(1));
    }

    @Test
    public void LruStrategyFullCacheDisplaceSecondValueAfterUpdateFirst() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(5).get(), is(2));
    }

    @Test
    public void LruUpdateExistingElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(1);
        assertThat(strategy.put(4).get(), is(2));
    }

    @Test
    public void LruUpdateNonExistingElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.update(0);
        assertThat(strategy.put(4).get(), is(1));
    }

    @Test
    public void LruContainsKey() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        strategy.put(4);
        assertThat(strategy.containsKey(2), is(true));
    }

    @Test
    public void LruParentClassGetCapacity() {
        assertThat(strategy.getCapacity(), is(3));
    }

    @Test(expected = NullPointerException.class)
    public void LruNullKey() {
        strategy.put(null);
    }

    @Test
    public void LruStaticInit() {
        Strategy.Lru(5);
    }

    @Test
    public void LruRemove() {
        strategy.put(1);
        strategy.put(2);
        strategy.remove(1);
        strategy.put(3);
        strategy.put(4);
        strategy.update(2);
        assertThat(strategy.put(5).get(), is(3));
    }
}
