package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.Bicycle;
import ua.com.alevel.alexshent.model.Boat;
import ua.com.alevel.alexshent.repository.Repository;
import ua.com.alevel.alexshent.service.AutomobileService;
import ua.com.alevel.alexshent.service.BicycleService;
import ua.com.alevel.alexshent.service.BoatService;

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
        Repository<Boat> boatRepository = boatService.getRepository();
        Boat boat = boatRepository.getById(targetBoatId);
        System.out.println("------------------");
        boatService.printAll();
        boat.setModel("m-m-m");
        boatRepository.update(boat);
        System.out.println("------------------");
        boatService.printAll();
    }

    public void deleteBoatProduct() {
        Repository<Boat> boatRepository = boatService.getRepository();
        System.out.println("------------------");
        boatService.printAll();
        boatRepository.delete(targetBoatId);
        System.out.println("------------------");
        boatService.printAll();
    }
}
