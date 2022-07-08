package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;

public class Boat extends Vehicle {
    private boolean withEngine;

    public Boat(String model, Manufacturer manufacturer, BigDecimal price, boolean withEngine) {
        super(model, manufacturer, price);
        this.withEngine = withEngine;
    }

    public boolean isWithEngine() {
        return withEngine;
    }

    public void setWithEngine(boolean withEngine) {
        this.withEngine = withEngine;
    }

    public static void copy(Boat source, Boat destination) {
        destination.setModel(source.getModel());
        destination.setPrice(source.getPrice());
        destination.setManufacturer(source.getManufacturer());
        destination.setWithEngine(source.isWithEngine());
    }

    @Override
    public String toString() {
        return "Boat {" +
                "withEngine=" + withEngine +
                ", id=" + id +
                ", model=" + model +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
