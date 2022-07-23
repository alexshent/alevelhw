package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.repository.Repository;
import ua.com.alevel.alexshent.service.AutomobileService;
import ua.com.alevel.alexshent.service.BicycleService;
import ua.com.alevel.alexshent.service.BoatService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
        Vehicle vehicle = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(123.45), "BBB");
        Refresh refresh1 = new Refresh(vehicle, 1, LocalDateTime.now());
        Refresh refresh2 = new Refresh(vehicle, 2, LocalDateTime.now());
        Refresh refresh3 = new Refresh(vehicle, 3, LocalDateTime.now());
        Garage garage = new Garage();
        garage.add(refresh3);
        garage.add(refresh2);
        garage.add(refresh1);
        System.out.println("size = " + garage.getSize());
        System.out.println("first = " + garage.getFirstDate());
        System.out.println("last = " + garage.getLastDate());
        System.out.println(garage.findById(2).toString());
        Refresh refresh4 = new Refresh(vehicle, 4, LocalDateTime.now());
        garage.replace(1, refresh4);
        garage.delete(2);
        for (Refresh refresh : garage) {
            System.out.println(refresh);
        }
    }

    public void useVehicleComparators() {
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
