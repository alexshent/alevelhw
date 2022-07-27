package ua.com.alevel.alexshent.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> getById(String id);
    List<Optional<T>> getAll();
    boolean add(T vehicle);
    boolean add(Optional<T> vehicleOptional);
    boolean addList(List<T> vehicle);
    boolean update(T vehicle);
    boolean delete(String id);
}
