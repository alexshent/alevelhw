package com.example.model;

import java.math.BigDecimal;

/**
 * товар, несколько штук которого можно склеить скотчем в Bundle или положить одну штуку в коробку Box
 */
public class Product implements Packable {
    private long id;
    private String title;
    private BigDecimal price;
    private int amountAvailable;

    public Product(long id, String title, BigDecimal price, int amountAvailable) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    @Override
    public String toString() {
        return """
               Product {
               id = %d
               title = %s
               price = %s
               amount available = %d
               }
               """
                .formatted(id, title, price.toString(), amountAvailable);
    }
}
