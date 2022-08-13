package com.example;

import com.example.event.Event;
import com.example.model.Box;
import com.example.model.Bundle;
import com.example.model.Customer;
import com.example.model.Product;
import com.example.service.EventService;
import com.example.service.ShippingService;
import com.example.service.StorageService;

import java.math.BigDecimal;

public class Main {
    StorageService storageService = new StorageService();
    ShippingService shippingService = new ShippingService();
    private static final String FIRST_CUSTOMER_ADDRESS = "Address 1";
    private static final String SECOND_CUSTOMER_ADDRESS = "Address 2";
    private static final String CONTAINER_SHIPPED_MESSAGE = "container shipped: ";

    /**
     * упаковать товары в коробки, отправить коробки на склад
     * затем извлечь коробки со склада и отправить
     */
    private void useStorage() {
        // create customers and subscribe them to events
        Customer customer1 = new Customer();
        EventService.getInstance().subscribeObserverToEvent(Event.BOX_SHIPPED_TO_CUSTOMER1, customer1);
        Customer customer2 = new Customer();
        EventService.getInstance().subscribeObserverToEvent(Event.BOX_SHIPPED_TO_CUSTOMER2, customer2);

        // put one item of product1 into the box and send it to the storage
        Product product1 = new Product(1, "product1", BigDecimal.valueOf(111.00), 11);
        Box box1 = new Box();
        box1.add(product1);
        box1.setDestinationAddress(FIRST_CUSTOMER_ADDRESS);
        box1.setStorageId("s1");
        storageService.storeItem(box1);

        // put a bundle of product2 into the box and send it to the storage
        Product product2 = new Product(2, "product2", BigDecimal.valueOf(222.00), 22);
        Bundle bundleOfProduct2 = new Bundle(product2, 9);
        Box box2 = new Box();
        box2.add(bundleOfProduct2);
        box2.setDestinationAddress(SECOND_CUSTOMER_ADDRESS);
        box2.setStorageId("s2");
        storageService.storeItem(box2);

        // put one item of product3 into the box and send it to the storage
        Product product3 = new Product(3, "product3", BigDecimal.valueOf(333.00), 33);
        Box box3 = new Box();
        box3.add(product3);
        box3.setDestinationAddress(SECOND_CUSTOMER_ADDRESS);
        box3.setStorageId("s3");
        storageService.storeItem(box3);

        // remove s1 box from the storage and ship it
        Box box = (Box) storageService.removeItem("s1");
        shippingService.ship(box, (container) -> EventService.getInstance().informObserversAboutEvent(
                Event.BOX_SHIPPED_TO_CUSTOMER1,
                CONTAINER_SHIPPED_MESSAGE + container.toString()
        ));

        // remove s2 box from the storage and ship it
        box = (Box) storageService.removeItem("s2");
        shippingService.ship(box, (container) -> EventService.getInstance().informObserversAboutEvent(
                Event.BOX_SHIPPED_TO_CUSTOMER2,
                CONTAINER_SHIPPED_MESSAGE + container.toString()
        ));

        // remove s3 box from the storage and ship it
        box = (Box) storageService.removeItem("s3");
        shippingService.ship(box, (container) -> EventService.getInstance().informObserversAboutEvent(
                Event.BOX_SHIPPED_TO_CUSTOMER2,
                CONTAINER_SHIPPED_MESSAGE + container.toString()
        ));

        // unsubscribe customers from events
        EventService.getInstance().unsubscribeObserverFromEvent(Event.BOX_SHIPPED_TO_CUSTOMER1, customer1);
        EventService.getInstance().unsubscribeObserverFromEvent(Event.BOX_SHIPPED_TO_CUSTOMER2, customer2);
    }

    /**
     * упаковать товары в коробки
     * затем отправить коробки без складирования
     */
    private void noStorage() {
        // create customers and subscribe them to events
        Customer customer1 = new Customer();
        EventService.getInstance().subscribeObserverToEvent(Event.BOX_SHIPPED_TO_CUSTOMER1, customer1);
        Customer customer2 = new Customer();
        EventService.getInstance().subscribeObserverToEvent(Event.BOX_SHIPPED_TO_CUSTOMER2, customer2);

        // put one item of product1 into the box
        Product product1 = new Product(1, "product1", BigDecimal.valueOf(111.00), 11);
        Box box1 = new Box();
        box1.add(product1);
        box1.setDestinationAddress(FIRST_CUSTOMER_ADDRESS);

        // put a bundle of product2 into the box
        Product product2 = new Product(2, "product2", BigDecimal.valueOf(222.00), 22);
        Bundle bundleOfProduct2 = new Bundle(product2, 9);
        Box box2 = new Box();
        box2.add(bundleOfProduct2);
        box2.setDestinationAddress(SECOND_CUSTOMER_ADDRESS);

        // put one item of product3 into the box
        Product product3 = new Product(3, "product3", BigDecimal.valueOf(333.00), 33);
        Box box3 = new Box();
        box3.add(product3);
        box3.setDestinationAddress(SECOND_CUSTOMER_ADDRESS);

        // ship first box to customer 1
        shippingService.ship(box1, (container) -> EventService.getInstance().informObserversAboutEvent(
                Event.BOX_SHIPPED_TO_CUSTOMER1,
                CONTAINER_SHIPPED_MESSAGE + container.toString()
        ));

        // ship second box to customer 2
        shippingService.ship(box2, (container) -> EventService.getInstance().informObserversAboutEvent(
                Event.BOX_SHIPPED_TO_CUSTOMER2,
                CONTAINER_SHIPPED_MESSAGE + container.toString()
        ));

        // ship third box to customer 2
        shippingService.ship(box3, (container) -> EventService.getInstance().informObserversAboutEvent(
                Event.BOX_SHIPPED_TO_CUSTOMER2,
                CONTAINER_SHIPPED_MESSAGE + container.toString()
        ));

        // unsubscribe customers from events
        EventService.getInstance().unsubscribeObserverFromEvent(Event.BOX_SHIPPED_TO_CUSTOMER1, customer1);
        EventService.getInstance().unsubscribeObserverFromEvent(Event.BOX_SHIPPED_TO_CUSTOMER2, customer2);
    }

    /**
     * упаковать товары в коробки и отправить коробки на отправочный склад
     * затем разослать все коробки с отправочного склада
     */
    private void useShippingServiceStorage() {
        // create customers and subscribe them to events
        Customer customer1 = new Customer();
        EventService.getInstance().subscribeObserverToEvent(Event.BOX_SHIPPED_TO_CUSTOMER1, customer1);
        Customer customer2 = new Customer();
        EventService.getInstance().subscribeObserverToEvent(Event.BOX_SHIPPED_TO_CUSTOMER2, customer2);

        // put one item of product1 into the box
        Product product1 = new Product(1, "product1", BigDecimal.valueOf(111.00), 11);
        Box box1 = new Box();
        box1.add(product1);
        box1.setDestinationAddress(FIRST_CUSTOMER_ADDRESS);
        shippingService.storeItem(box1);

        // put a bundle of product2 into the box
        Product product2 = new Product(2, "product2", BigDecimal.valueOf(222.00), 22);
        Bundle bundleOfProduct2 = new Bundle(product2, 9);
        Box box2 = new Box();
        box2.add(bundleOfProduct2);
        box2.setDestinationAddress(SECOND_CUSTOMER_ADDRESS);
        shippingService.storeItem(box2);

        // put one item of product3 into the box
        Product product3 = new Product(3, "product3", BigDecimal.valueOf(333.00), 33);
        Box box3 = new Box();
        box3.add(product3);
        box3.setDestinationAddress(SECOND_CUSTOMER_ADDRESS);
        shippingService.storeItem(box3);

        // ship all boxes
        shippingService.shipAllStored(
                (container) -> {
                    if (container.getDestinationAddress().equals(FIRST_CUSTOMER_ADDRESS)) {
                        EventService.getInstance().informObserversAboutEvent(
                                Event.BOX_SHIPPED_TO_CUSTOMER1,
                                CONTAINER_SHIPPED_MESSAGE + container.toString()
                        );
                    }
                    else if (container.getDestinationAddress().equals(SECOND_CUSTOMER_ADDRESS)) {
                        EventService.getInstance().informObserversAboutEvent(
                                Event.BOX_SHIPPED_TO_CUSTOMER2,
                                CONTAINER_SHIPPED_MESSAGE + container.toString()
                        );
                    }
        });

        // unsubscribe customers from events
        EventService.getInstance().unsubscribeObserverFromEvent(Event.BOX_SHIPPED_TO_CUSTOMER1, customer1);
        EventService.getInstance().unsubscribeObserverFromEvent(Event.BOX_SHIPPED_TO_CUSTOMER2, customer2);
    }

    public static void main(String[] args) {
        Main main = new Main();

        main.useStorage();
        System.out.println("========================\n");

        main.noStorage();
        System.out.println("========================\n");

        main.useShippingServiceStorage();
    }
}
