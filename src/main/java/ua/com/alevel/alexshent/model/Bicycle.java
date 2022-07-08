package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;

public class Bicycle extends Vehicle {
    private int numberOfWheels;

    public Bicycle(String model, Manufacturer manufacturer, BigDecimal price, int numberOfWheels) {
        super(model, manufacturer, price);
        this.numberOfWheels = numberOfWheels;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public static void copy(Bicycle source, Bicycle destination) {
        destination.setModel(source.getModel());
        destination.setPrice(source.getPrice());
        destination.setManufacturer(source.getManufacturer());
        destination.setNumberOfWheels(source.getNumberOfWheels());
    }

    @Override
    public String toString() {
        return "Bicycle {" +
                "numberOfWheels=" + numberOfWheels +
                ", id=" + id +
                ", model=" + model +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
