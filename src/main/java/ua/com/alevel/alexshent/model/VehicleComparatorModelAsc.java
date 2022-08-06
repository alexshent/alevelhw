package ua.com.alevel.alexshent.model;

import java.util.Comparator;

public class VehicleComparatorModelAsc<V extends Vehicle> implements Comparator<V> {
    @Override
    public int compare(V first, V second) {
        // model, asc
        return first.getModel().compareTo(second.getModel());
    }
}
