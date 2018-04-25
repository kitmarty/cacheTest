package com.kitmarty.cachetest.test.strategy;

import com.kitmarty.cachetest.strategy.MruStrategyFast;
import com.kitmarty.cachetest.strategy.Strategy;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("Duplicates")
public class MruStrategyFastTest {

    private final Strategy<Integer> strategy = new MruStrategyFast<>(3);

    @Test
    public void updateElementAndThenDisplaceItWithPut() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        // < 3 2 1 >
        strategy.update(1);
        // < 1 3 2 >
        assertThat(strategy.put(4), is(Optional.of(1)));
        // < 4 3 2 >
        assertThat(strategy.put(5), is(Optional.of(4)));
    }

    @Test
    public void updateNonExistingElement() {
        assertThat(strategy.update(1), is(false));
    }

    @Test
    public void putExistingElement() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        // < 3 2 1 >
        strategy.put(2);
        // < 2 3 1 >
        assertThat(strategy.put(4), is(Optional.of(2)));
    }

    @Test
    public void keyRemove() {
        strategy.put(1);
        strategy.put(2);
        strategy.put(3);
        // < 3 2 1 >
        strategy.remove(1);
        // < 3 2 . >
        assertThat(strategy.getSize(), is(2));
        strategy.put(4);
        // < 4 3 2 >
        assertThat(strategy.put(5), is(Optional.of(4)));
        // < 5 3 2 >
    }

    @Test
    public void containsExistingKey() {
        strategy.put(2);
        assertThat(strategy.containsKey(2), is(true));
    }

    @Test
    public void containsNonExistingKey() {
        assertThat(strategy.containsKey(2), is(false));
    }
}
