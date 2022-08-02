package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.service.AutomobileService;
import ua.com.alevel.alexshent.service.BicycleService;
import ua.com.alevel.alexshent.service.BoatService;

import java.math.BigDecimal;
import java.util.List;

public class Demo {
    private final AutomobileService autoService = new AutomobileService();
    private final BicycleService bicycleService = new BicycleService();
    private final BoatService boatService = new BoatService();
    String targetBoatId;
    private final String separatorLine = "------------------";

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
        Boat  boat = boatService.getProductById(targetBoatId);
        System.out.println(separatorLine);
        boatService.printAll();
        boat.setModel("m-m-m");
        boatService.updateProduct(boat);
        System.out.println(separatorLine);
        boatService.printAll();
    }

    public void deleteBoatProduct() {
        System.out.println(separatorLine);
        boatService.printAll();
        boatService.deleteProduct(targetBoatId);
        System.out.println(separatorLine);
        boatService.printAll();
    }

    public void useContainer() {
        Container<Boat, Integer> container = new Container<>();
        Boat boat = new Boat("AAA", BoatManufactures.FFF, BigDecimal.valueOf(1000.00), true);
        container.addVehicle(boat);
        System.out.println(separatorLine);
        System.out.println("vehicle class = " + container.getVehicleClassName());
        System.out.println("price = " + container.getVehiclePrice());
        System.out.println("discount price = " + container.getDiscountPrice().toString());
        System.out.println("extra charge price = " + container.getExtraChargePrice(100));
    }
}
