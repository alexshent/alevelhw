package ua.com.alevel.alexshent.service;

import ua.com.alevel.alexshent.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class Service<V>  {
    protected Repository<V> repository;
    protected static final Random RANDOM = new Random();

    public void printAll() {
        for (Optional<V> vehicleOptional : repository.getAll()) {
            vehicleOptional.ifPresent(System.out::println);
        }
    }

    public void saveProducts(List<V> vehicles) {
        repository.addList(vehicles);
    }

    public Optional<V> getProductById(String id) {
        return repository.getById(id);
    }

    public boolean updateProduct(V product) {
        return repository.update(product);
    }

    public boolean deleteProduct(String id) {
        return repository.delete(id);
    }
}
