package com.example.repository;

/**
 * объекты этого интерфейса могут быть отправлены заказчику по указанному адресу
 */
public interface Shipable {
    String getDestinationAddress();
    void setDestinationAddress(String address);
}
