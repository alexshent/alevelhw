package com.example.repository;

import java.util.LinkedList;
import java.util.List;

/**
 * склад, в котором можно хранить Storable объекты
 */
public class Storage {
    private final List<Storable> items = new LinkedList<>();

    public void store(Storable item) {
        items.add(item);
    }

    public Storable remove(String storageId) {
        for (Storable item : items) {
            if (item.getStorageId().equals(storageId)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
