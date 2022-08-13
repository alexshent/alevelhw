package com.example.repository;

import java.util.LinkedList;
import java.util.Queue;

/**
 * склад, с которого клиентам отправляются заказы (коробки с товарами)
 */
public class ShippingStorage {
    private final Queue<Storable> items = new LinkedList<>();

    public void store(Storable item) {
        items.add(item);
    }

    public Storable remove() {
        return items.poll();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
