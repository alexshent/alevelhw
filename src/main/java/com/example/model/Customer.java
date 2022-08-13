package com.example.model;

import com.example.event.Event;
import com.example.event.Observer;

/**
 * клиент, которому отправляются коробки с заказами
 */
public class Customer implements Observer {
    @Override
    public void update(Event event, String message) {
        System.out.println("observer update: " + event + " " + message);
    }
}
