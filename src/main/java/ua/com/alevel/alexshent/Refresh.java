package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.Vehicle;

import java.time.LocalDateTime;
import java.util.Objects;

public class Refresh implements Comparable<Refresh> {
    protected Vehicle vehicle;
    protected int id;
    protected LocalDateTime createdAt;

    public Refresh(Vehicle vehicle, int id, LocalDateTime createdAt) {
        this.vehicle = vehicle;
        this.id = id;
        this.createdAt = createdAt;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int compareTo(Refresh refresh) {
        return id - refresh.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Refresh) {
            return this.id == ((Refresh) object).getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return  "id = " +
                id +
                "\n" +
                "created at = " +
                createdAt.toString() +
                "\n" +
                "vehicle:\n" +
                vehicle.toString();
    }
}
