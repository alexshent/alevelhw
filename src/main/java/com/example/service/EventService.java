package com.example.service;

import com.example.event.Event;
import com.example.event.Observer;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * подписывает наблюдателей на события и уведомляет подписчиков о событии
 * реализует паттерн Singleton
 */
public class EventService {
    Map<Event, List<Observer>> subscribers = new EnumMap<>(Event.class);
    private static EventService instance;

    private EventService() {}

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    /**
     * подписать наблюдателя на событие
     * @param event событие
     * @param observer наблюдатель
     */
    public void subscribeObserverToEvent(Event event, Observer observer) {
        List<Observer> listOfObservers = null;
        if (subscribers.containsKey(event)) {
            listOfObservers = subscribers.get(event);
        }
        if (listOfObservers == null) {
            listOfObservers = new ArrayList<>();
        }
        listOfObservers.add(observer);
        subscribers.put(event, listOfObservers);
    }

    /**
     * отписать наблюдателя от события
     * @param event событие
     * @param observer наблюдатель
     */
    public void unsubscribeObserverFromEvent(Event event, Observer observer) {
        if (subscribers.containsKey(event)) {
            List<Observer> listOfObservers = subscribers.get(event);
            if (listOfObservers != null && !listOfObservers.isEmpty() && listOfObservers.contains(observer)) {
                listOfObservers.remove(observer);
            }
        }
    }

    /**
     * уведомить наблюдателей о событии
     * @param event
     * @param message
     */
    public void informObserversAboutEvent(Event event, String message) {
        if (subscribers.containsKey(event)) {
            List<Observer> listOfObservers = subscribers.get(event);
            if (listOfObservers != null && !listOfObservers.isEmpty()) {
                for (Observer observer : listOfObservers) {
                    observer.update(event, message);
                }
            }
        }
    }
}
