package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.command.CommandExecutor;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.Bicycle;
import ua.com.alevel.alexshent.model.Boat;
import ua.com.alevel.alexshent.service.AutomobileService;
import ua.com.alevel.alexshent.service.BicycleService;
import ua.com.alevel.alexshent.service.BoatService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        Boat  boat = boatService.getProductById(targetBoatId);
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

    public void useMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        CommandExecutor commandExecutor = new CommandExecutor(new AutomobileService());
        int option = 0;
        do {
            System.out.println(commandExecutor.getOptions());
            try {
                String userInput = reader.readLine();
                option = Integer.parseInt(userInput);
                commandExecutor.executeCommand(option);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (option != 0);
    }
}
