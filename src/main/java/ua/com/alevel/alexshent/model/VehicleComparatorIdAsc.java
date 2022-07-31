package ua.com.alevel.alexshent.model;

import java.util.Comparator;

public class VehicleComparatorIdAsc<V extends Vehicle> implements Comparator<V> {
    @Override
    public int compare(V first, V second) {
        // id, asc
        return first.getId().compareTo(second.getId());
    }
}
