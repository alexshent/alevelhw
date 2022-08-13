package ua.com.alevel.alexshent.vtb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit> {
    private final List<T> fruit = new ArrayList<>();

    public void add(T item) {
        fruit.add(item);
    }

    public float getWeight() {
        float weight = 0.0f;
        for (T item : fruit) {
            weight += item.getWeight();
        }
        return weight;
    }

    public boolean compareWeight(Box<? extends Fruit> box) {
        BigDecimal myWeight = BigDecimal.valueOf(this.getWeight());
        myWeight.setScale(3, RoundingMode.CEILING);
        BigDecimal boxWeight = BigDecimal.valueOf(box.getWeight());
        boxWeight.setScale(3, RoundingMode.CEILING);
        return myWeight.compareTo(boxWeight) == 0;
    }

    public void moveContents(Box<T> box) {
        for (T item : fruit) {
            box.add(item);
        }
        fruit.clear();
    }
}
