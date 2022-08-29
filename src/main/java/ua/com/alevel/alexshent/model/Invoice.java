package ua.com.alevel.alexshent.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private String id;
    private LocalDateTime createdAt;
    private String name;
    private List<Vehicle> vehicles;

    public Invoice(InvoiceBuilder builder) {
        this.id = builder.getId();
        this.createdAt = builder.getCreatedAt();
        this.name = builder.getName();
        this.vehicles = builder.getVehicles();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Invoice invoice) {
            return
                    Objects.equals(this.id, invoice.getId()) &&
                    Objects.equals(this.createdAt, invoice.getCreatedAt()) &&
                    Objects.equals(this.name, invoice.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder vehiclesString = new StringBuilder();
        vehicles.forEach(v -> {
                    vehiclesString.append(v.toString());
                    vehiclesString.append("\n");
                }
        );
        return
                """
                        Invoice {
                        id = %s,
                        createdAt = %s,
                        name = %s,
                        vehicles {
                        %s}
                        }
                        """.formatted(id, createdAt, name, vehiclesString.toString());
    }
}
