package ua.com.alevel.alexshent.model;

import java.util.Comparator;

public class VehicleComparatorPriceDesc<V extends Vehicle> implements Comparator<V> {
    @Override
    public int compare(V first, V second) {
        // price, desc
        return second.getPrice().compareTo(first.getPrice());
    }
}
