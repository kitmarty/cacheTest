package com.kitmarty.cachetest.storage;

import java.io.*;
import java.util.Objects;
import java.util.Optional;

public class DiskStorage<K, V extends Serializable> implements Storage<K, V> {

    private final String storagePath;

    public DiskStorage(String storagePath) {
        Objects.requireNonNull(storagePath, "objectStorage");
        this.storagePath = storagePath;
    }

    public DiskStorage() {
        this.storagePath = System.getProperty("user.dir");
    }

    @Override
    public Optional<V> put(K key, V value) {
        File file = new File(storagePath, key.toString() + ".ser");
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(file)));
            oos.writeObject(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {
        File file = new File(storagePath, key.toString() + ".ser");
        ObjectInputStream ois = null;
        V valueToReturn = null;
        if (file.exists()) {
            try {
                ois = new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(file)));
                valueToReturn = (V) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return Optional.ofNullable(valueToReturn);
    }

    @Override
    public Optional<V> remove(K key) {
        File file = new File(storagePath, key.toString() + ".ser");
        Optional<V> valueToReturn = Optional.empty();
        if (file.exists()) {
            valueToReturn = get(key);
            file.delete();
        }
        return valueToReturn;
    }

    public void clear() {
        File dir = new File(this.storagePath);
        File[] filesToRemove = dir.listFiles((dir1, filename) -> filename.endsWith(".ser"));
        for (File file : filesToRemove) {
            file.delete();
        }
    }
}