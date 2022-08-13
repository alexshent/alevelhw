package com.example.service;

import com.example.repository.Shipable;
import com.example.repository.ShippingStorage;
import com.example.repository.Storable;

import java.util.function.Consumer;

/**
 * сервис отправки контейнеров заказчикам
 */
public class ShippingService {
    private final ShippingStorage storage = new ShippingStorage();

    public void storeItem(Storable item) {
        storage.store(item);
    }

    public Storable removeItem() {
        return storage.remove();
    }

    /**
     * отправить все контейнеры с локального склада
     * @param consumer функция, которая отработает после отправки
     */
    public void shipAllStored(Consumer<Shipable> consumer) {
        while (!storage.isEmpty()) {
            Storable container = storage.remove();
            this.ship((Shipable) container, consumer);
        }
    }

    /**
     * отправить один контейнер
     * @param container контейнер
     * @param consumer функция, которая отработает после отправки
     */
    public void ship(Shipable container, Consumer<Shipable> consumer) {
        System.out.println("ship()");
        if (consumer != null) {
            consumer.accept(container);
        }
    }
}
