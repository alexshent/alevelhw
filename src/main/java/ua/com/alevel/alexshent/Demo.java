package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.service.AutomobileService;
import ua.com.alevel.alexshent.service.BicycleService;
import ua.com.alevel.alexshent.service.BoatService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Demo {
    private final AutomobileService autoService = new AutomobileService();
    private final BicycleService bicycleService = new BicycleService();
    private final BoatService boatService = new BoatService();
    String targetBoatId;

    public void createProducts() {
        List<Automobile> autos = autoService.createAutos(10);
        autoService.saveProducts(autos);
        autoService.printAll();

        List<Bicycle> bicycles = bicycleService.createBicycles(10);
        bicycleService.saveProducts(bicycles);
        bicycleService.printAll();

        List<Boat> boats = boatService.createBoats(10);
        boatService.saveProducts(boats);
        boatService.printAll();
        targetBoatId = boats.get(0).getId();
    }

    public void changeBoatProduct() {
        Boat boat = boatService.getProductById(targetBoatId);
        System.out.println("------------------");
        boatService.printAll();
        boat.setModel("m-m-m");
        boatService.updateProduct(boat);
        System.out.println("------------------");
        boatService.printAll();
    }

    public void deleteBoatProduct() {
        System.out.println("------------------");
        boatService.printAll();
        boatService.deleteProduct(targetBoatId);
        System.out.println("------------------");
        boatService.printAll();
    }

    public void useGarage() {
        System.out.println("----- Garage -----");
        Automobile automobile1 = new Automobile("AAA1", AutomobileManufacturers.BMW, BigDecimal.valueOf(123.45), "BBB1");
        Automobile automobile2 = new Automobile("AAA2", AutomobileManufacturers.BMW, BigDecimal.valueOf(123.45), "BBB2");
        Garage<Automobile> garage = new Garage<>();
        garage.add(automobile1, 1);
        garage.add(automobile1, 2);
        garage.add(automobile1, 3);
        System.out.println("size = " + garage.getSize());
        System.out.println("first = " + garage.getFirstDate());
        System.out.println("last = " + garage.getLastDate());
        System.out.println(garage.getVehicleForRefreshNumber(2));
        garage.setVehicleForRefreshNumber(3, automobile2);
        garage.removeNodeByRefreshNumber(1);
        for (Automobile automobile : garage) {
            System.out.println(automobile);
        }
    }

    public void useVehicleComparators() {
        System.out.println("----- Comparators -----");
        class VehicleComparatorPriceDesc<V extends Vehicle> implements Comparator<V> {
            @Override
            public int compare(V first, V second) {
                // price, desc
                return second.getPrice().compareTo(first.getPrice());
            }
        }

        class VehicleComparatorModelAsc<V extends Vehicle> implements Comparator<V> {
            @Override
            public int compare(V first, V second) {
                // model, asc
                return first.getModel().compareTo(second.getModel());
            }
        }

        class VehicleComparatorIdAsc<V extends Vehicle> implements Comparator<V> {
            @Override
            public int compare(V first, V second) {
                // id, asc
                return first.getId().compareTo(second.getId());
            }
        }

        List<Automobile> list = new ArrayList<>();
        Automobile automobile1 = new Automobile("AAA1", AutomobileManufacturers.BMW, BigDecimal.valueOf(1.99), "BBB1");
        Automobile automobile2 = new Automobile("AAA2", AutomobileManufacturers.BMW, BigDecimal.valueOf(2.99), "BBB2");
        Automobile automobile3 = new Automobile("AAA3", AutomobileManufacturers.BMW, BigDecimal.valueOf(3.99), "BBB3");
        Automobile automobile4 = new Automobile("AAA4", AutomobileManufacturers.BMW, BigDecimal.valueOf(4.99), "BBB4");
        Automobile automobile5 = new Automobile("AAA5", AutomobileManufacturers.BMW, BigDecimal.valueOf(5.99), "BBB5");
        list.add(automobile1);
        list.add(automobile2);
        list.add(automobile3);
        list.add(automobile4);
        list.add(automobile5);
        Comparator<Automobile> comparator = new VehicleComparatorPriceDesc<Automobile>()
                .thenComparing(new VehicleComparatorModelAsc<>())
                .thenComparing(new VehicleComparatorIdAsc<>());
        list.sort(comparator);
        for (Automobile automobile : list) {
            System.out.println(automobile.toString());
        }
    }
}
