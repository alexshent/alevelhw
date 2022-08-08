package com.example.event;

/**
 * интерфейс подписчика на событие
 */
public interface Observer {
    /**
     * этот метод вызывается уведомителем при возникновении подписанного события
     * @param event событие
     * @param message сообщение
     */
    void update(Event event, String message);
}
