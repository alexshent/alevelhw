package com.example.repository;

/**
 * объект этого интерфейса можно хранить на складе
 */
public interface Storable {
    String getStorageId();
    void setStorageId(String storageId);
}
