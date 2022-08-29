package ua.com.alevel.alexshent.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> getById(String id);
    List<Optional<T>> getAll();
    boolean add(T item);
    boolean add(Optional<T> itemOptional);
    boolean addList(List<T> items);
    boolean update(T item);
    boolean delete(String id);
}
