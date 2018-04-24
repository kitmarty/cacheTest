package com.kitmarty.cachetest.storage;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class DiskStorage<K, V extends Serializable>
        implements Storage<K, V> {

    private static final String FILE_EXTENSION = ".ser";

    private static int storageCounter = 0;

    private int storageID;
    private int elementID;
    private final String storagePath;
    private final HashMap<K, Integer> storageHash = new HashMap<>();

    public DiskStorage(String storagePath) {
        Objects.requireNonNull(storagePath, "objectStorage");
        this.storagePath = storagePath;
        this.storageID = ++storageCounter;
    }

    public DiskStorage() {
        this.storagePath = System.getProperty("user.dir");
        this.storageID = ++storageCounter;
    }

    @Override
    public Optional<V> put(K key, V value) {
        storageHash.put(key, ++elementID);
        Path filepath = Paths.get(storagePath, String.valueOf(storageID) + "_" + (String.valueOf(elementID)) + FILE_EXTENSION);

        try {
            Files.write(filepath, SerializationUtils.serialize(value));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {

        Integer link = storageHash.get(key);
        Optional<V> valueToReturn = Optional.empty();

        if (link != null) {
            Path filepath = Paths.get(storagePath, String.valueOf(storageID) + "_" + (String.valueOf(link)) + FILE_EXTENSION);

            try {
                valueToReturn = Optional.ofNullable(SerializationUtils.deserialize(Files.readAllBytes(filepath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return valueToReturn;
    }

    @Override
    public Optional<V> remove(K key) {

        Integer link = storageHash.get(key);
        Optional<V> valueToReturn = get(key);

        if (link != null) {
            storageHash.remove(key);
            Path filepath = Paths.get(storagePath, String.valueOf(storageID) + "_" + (String.valueOf(link)) + FILE_EXTENSION);
            try {
                Files.deleteIfExists(filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return valueToReturn;
    }

    public void clear() {
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(storagePath), "*" + FILE_EXTENSION);
            for (Path entry : stream) {
                Files.deleteIfExists(entry);
            }
            stream.close();
            storageHash.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}