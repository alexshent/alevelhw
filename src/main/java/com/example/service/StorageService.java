package com.example.service;

import com.example.repository.Storable;
import com.example.repository.Storage;

/**
 * сервис складирования контейнеров
 */
public class StorageService {
    private final Storage storage = new Storage();

    public void storeItem(Storable item) {
        storage.store(item);
    }

    public Storable removeItem(String storageId) {
        return storage.remove(storageId);
    }
}
