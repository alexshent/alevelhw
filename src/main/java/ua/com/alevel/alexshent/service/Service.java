package ua.com.alevel.alexshent.service;

import ua.com.alevel.alexshent.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class Service<T>  {
    protected Repository<T> repository;
    protected static final Random RANDOM = new Random();

    public void printAll() {
        for (Optional<T> vehicleOptional : repository.getAll()) {
            vehicleOptional.ifPresent(System.out::println);
        }
    }

    public void saveProducts(List<T> products) {
        repository.addList(products);
    }

    public Optional<T> getProductById(String id) {
        return repository.getById(id);
    }

    public boolean updateProduct(T product) {
        return repository.update(product);
    }

    public boolean deleteProduct(String id) {
        return repository.delete(id);
    }
}
