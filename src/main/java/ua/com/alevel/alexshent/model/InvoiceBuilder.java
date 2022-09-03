package ua.com.alevel.alexshent.model;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceBuilder {
    private String id;
    private LocalDateTime createdAt;
    private String name;
    private List<Vehicle> vehicles;

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public InvoiceBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public InvoiceBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public InvoiceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public InvoiceBuilder withVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
        return this;
    }

    public Invoice build() {
        return new Invoice(this);
    }
}
