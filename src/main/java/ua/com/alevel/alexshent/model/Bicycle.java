package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Bicycle extends Vehicle {
    private BicycleManufactures manufacturer;
    private int numberOfWheels;

    public Bicycle(String model, BicycleManufactures manufacturer, BigDecimal price, int numberOfWheels) {
        super(model, price);
        this.manufacturer = manufacturer;
        this.numberOfWheels = numberOfWheels;
    }

    public BicycleManufactures getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(BicycleManufactures manufacturer) {
        this.manufacturer = manufacturer;
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
    public int hashCode() {
        return Objects.hash(id, numberOfWheels);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Bicycle) {
            return
                    Objects.equals(model, ((Bicycle) object).getModel())
                            && Objects.equals(price, ((Bicycle) object).getPrice())
                            && this.manufacturer == ((Bicycle) object).getManufacturer()
                            && this.numberOfWheels == ((Bicycle) object).getNumberOfWheels();
        }
        return false;
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
