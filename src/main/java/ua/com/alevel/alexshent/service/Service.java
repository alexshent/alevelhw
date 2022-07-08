package ua.com.alevel.alexshent.service;

import ua.com.alevel.alexshent.model.Manufacturer;
import ua.com.alevel.alexshent.repository.Repository;

import java.util.List;
import java.util.Random;

public abstract class Service<V>  {
    protected Repository<V> repository;
    protected static final Random RANDOM = new Random();

    public Repository<V> getRepository() {
        return repository;
    }

    public void printAll() {
        for (V vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }

    public void saveProducts(List<V> vehicles) {
        repository.create(vehicles);
    }

    protected abstract Manufacturer getRandomManufacturer();
}
