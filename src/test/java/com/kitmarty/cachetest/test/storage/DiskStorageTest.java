package com.kitmarty.cachetest.test.storage;

import com.kitmarty.cachetest.storage.DiskStorage;
import org.junit.After;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DiskStorageTest {
    private final DiskStorage<Integer, String> storage = new DiskStorage<>();

    @Test
    public void putAndGet() {
        storage.put(1, "One");
        assertThat(storage.get(1), is(Optional.of("One")));
    }

    @Test
    public void putAndRemoveExistingKey() {
        storage.put(1, "One");
        assertThat(storage.remove(1), is(Optional.of("One")));
        assertThat(storage.get(1), is(Optional.empty()));
    }

    @Test
    public void getNonExistingKey() {
        assertThat(storage.get(1), is(Optional.empty()));
    }

    @Test
    public void removeNonExistingKey() {
        assertThat(storage.remove(1), is(Optional.empty()));
    }

    @After
    public void clear() {
        storage.clear();
    }
}
