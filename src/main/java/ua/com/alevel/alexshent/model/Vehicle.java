package ua.com.alevel.alexshent.model;

import ua.com.alevel.alexshent.binarytree.Costly;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Vehicle implements Costly {
    protected String id;
    protected String model;
    protected BigDecimal price;

    protected Vehicle(String model, BigDecimal price) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
