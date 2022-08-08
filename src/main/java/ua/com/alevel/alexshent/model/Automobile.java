package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Automobile extends Vehicle {
    private AutomobileManufacturers manufacturer;
    private String bodyType;
    private LocalDateTime createdAt;
    private long tripCounter;
    private Engine engine;

    public Automobile(String model, AutomobileManufacturers manufacturer, BigDecimal price, String bodyType) {
        super(model, price);
        this.manufacturer = manufacturer;
        this.bodyType = bodyType;
        createdAt = LocalDateTime.now();
    }

    public Automobile(AutomobileBuilder automobileBuilder) {
        super(automobileBuilder.getModel(), automobileBuilder.getPrice());
        this.id = automobileBuilder.getId();
        this.bodyType = automobileBuilder.getBodyType();
        this.manufacturer = automobileBuilder.getManufacturer();
    }

    public AutomobileManufacturers getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(AutomobileManufacturers manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getTripCounter() {
        return tripCounter;
    }

    public void setTripCounter(long tripCounter) {
        this.tripCounter = tripCounter;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Automobile) {
            return
                    Objects.equals(model, ((Automobile) object).getModel())
                            && Objects.equals(price, ((Automobile) object).getPrice())
                            && this.manufacturer == ((Automobile) object).getManufacturer()
                            && Objects.equals(bodyType, ((Automobile) object).getBodyType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bodyType);
    }

    @Override
    public String toString() {
        return "Auto {" +
                "bodyType=" + bodyType +
                ", id=" + id +
                ", model=" + model +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }

    public static void copy(Automobile source, Automobile destination) {
        destination.setModel(source.getModel());
        destination.setPrice(source.getPrice());
        destination.setManufacturer(source.getManufacturer());
        destination.setBodyType(source.getBodyType());
    }
}
