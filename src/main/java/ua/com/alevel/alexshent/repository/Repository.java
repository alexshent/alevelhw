package ua.com.alevel.alexshent.repository;

import java.util.List;

public interface Repository<T> {
    T getById(String id);
    List<T> getAll();
    boolean create(T auto);
    boolean create(List<T> auto);
    boolean update(T auto);
    boolean delete(String id);
}
