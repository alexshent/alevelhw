package ua.com.alevel.alexshent.binarytree;

import ua.com.alevel.alexshent.model.*;

import java.math.BigDecimal;
import java.util.Comparator;

public class Demo {
    public void demo() {
        Comparator<Automobile> comparator = new VehicleComparatorPriceDesc<Automobile>()
                .thenComparing(new VehicleComparatorModelAsc<>())
                .thenComparing(new VehicleComparatorIdAsc<>());
        BinaryTree<Automobile> binaryTree = new BinaryTree<>(comparator);
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(1000), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(1001), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(900), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(333), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(1222), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(444), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(122), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(555), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(666), "BBB"));
        binaryTree.add(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(777), "BBB"));
        System.out.println(binaryTree);
        System.out.println("left branch cost = " + binaryTree.getLeftBranchCost());
        System.out.println("right branch cost = " + binaryTree.getRightBranchCost());
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.demo();
    }
}
