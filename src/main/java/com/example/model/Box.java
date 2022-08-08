package com.example.model;

import com.example.repository.Shipable;
import com.example.repository.Storable;

import java.util.ArrayList;
import java.util.List;

/**
 * контейнер, в который можно сложить Packable объекты и наклеить на коробку адрес получателя
 */
public class Box implements Storable, Shipable {
    private String destinationAddress;
    private final List<Packable> items = new ArrayList<>();
    private String storageId;

    public void add(Packable item) {
        items.add(item);
    }

    public List<Packable> getItems() {
        return items;
    }

    @Override
    public String getDestinationAddress() {
        return destinationAddress;
    }

    @Override
    public void setDestinationAddress(String address) {
        destinationAddress = address;
    }

    @Override
    public String getStorageId() {
        return storageId;
    }

    @Override
    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Box, destination address = ");
        sb.append(destinationAddress);
        sb.append("\n");
        for (Packable item : items) {
            sb.append(item.toString());
        }
        return sb.toString();
    }
}
