package com.kitmarty.cachetest.storage;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class DiskStorage<K, V extends Serializable>
        implements Storage<K, V> {

    private static final String FILE_EXTENSION = ".ser";

    private static int storageCounter = 0;

    private int elementId = 0;
    private final Path storagePath;
    private final HashMap<K, Path> storageHash = new HashMap<>();

    public DiskStorage(String storagePath) {
        Objects.requireNonNull(storagePath, "objectStorage");
        this.storagePath = Paths.get(storagePath).resolve(Paths.get(String.valueOf(++storageCounter)));
        try {
            Files.createDirectory(this.storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DiskStorage() {
        this.storagePath = Paths.get(System.getProperty("user.dir")).resolve(Paths.get(String.valueOf(++storageCounter)));
        try {
            Files.createDirectory(this.storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<V> put(K key, V value) {
        storageHash.put(key, Paths.get(String.valueOf(++elementId) + FILE_EXTENSION));
        Path filepath = storagePath.resolve(storageHash.get(key));

        //serialization to byte array and writing to file
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(value);
            out.flush();
            Files.write(filepath, bos.toByteArray(), StandardOpenOption.CREATE/*SerializationUtils.serialize(value)*/);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {

        Optional<V> valueToReturn = Optional.empty();
        Path partFilePath = storageHash.get(key);

        if (partFilePath != null) {
            Path filepath = storagePath.resolve(partFilePath);
            try (ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(Files.readAllBytes(filepath)))) {
                valueToReturn = Optional.ofNullable((V) in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return valueToReturn;
    }

    @Override
    public Optional<V> remove(K key) {

        Optional<V> valueToReturn = get(key);
        Path partFilePath = storageHash.get(key);
        if (partFilePath != null) {
            Path filepath = storagePath.resolve(storageHash.get(key));
            storageHash.remove(key);
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
            DirectoryStream<Path> stream = Files.newDirectoryStream(storagePath);
            for (Path entry : stream) {
                Files.deleteIfExists(entry);
            }
            stream.close();
            Files.deleteIfExists(storagePath);
            storageHash.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}