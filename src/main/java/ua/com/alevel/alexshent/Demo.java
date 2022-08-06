package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.Bicycle;
import ua.com.alevel.alexshent.model.Boat;
import ua.com.alevel.alexshent.reader.*;
import ua.com.alevel.alexshent.service.AutomobileService;
import ua.com.alevel.alexshent.service.BicycleService;
import ua.com.alevel.alexshent.service.BoatService;

import java.net.URISyntaxException;
import java.nio.file.Path;
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
        Boat  boat = boatService.getProductById(targetBoatId).orElseThrow();
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

    public void readObjectsFromFiles() {

        // xml
        try {
            String resourceFileName = "automobiles.xml";
            Path path = Path.of(ClassLoader.getSystemResource(resourceFileName).toURI());
            AutomobilesListRegularExpressions automobilesListRegularExpressions =
                    new AutomobilesListRegularExpressions(
                            "<automobiles>(.+)</automobiles>"
                    );
            AutomobileRegularExpressions automobileRegularExpressions =
                    new AutomobileRegularExpressions(
                            "<model>.+?</engine>",
                            "<model>(.+)</model>",
                            "<price.*>(.+)</price>",
                            "<manufacturer>(.+)</manufacturer>",
                            "<bodyType>(.+)</bodyType>",
                            "<createdAt>(.+)</createdAt>",
                            "<tripCounter>(.+)</tripCounter>"
                    );
            EngineRegularExpressions engineRegularExpressions =
                    new EngineRegularExpressions(
                            "<engine>(.+)</engine>",
                            "<volume>(.+)</volume>",
                            "<brand>(.+)</brand>"
                    );
            AutomobileParser automobileParser = new AutomobileParser();
            automobileParser.setAutomobilesListRegularExpressions(automobilesListRegularExpressions);
            automobileParser.setAutomobileRegularExpressions(automobileRegularExpressions);
            automobileParser.setEngineRegularExpressions(engineRegularExpressions);
            List<Automobile> list = automobileParser.parseAutomobilesList(path);
            list.forEach(System.out::println);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // json
        try {
            String resourceFileName = "automobiles.json";
            Path path = Path.of(ClassLoader.getSystemResource(resourceFileName).toURI());
            AutomobilesListRegularExpressions automobilesListRegularExpressions =
                    new AutomobilesListRegularExpressions(
                            "\\[(.+)\\]"
                    );
            AutomobileRegularExpressions automobileRegularExpressions =
                    new AutomobileRegularExpressions(
                            "\\{\"model\".+?\\}\\},?",
                            "\"model\":\"(.+?)\"",
                            "\"price\":\"(.+?)\"",
                            "\"manufacturer\":\"(.+?)\"",
                            "\"bodyType\":\"(.+?)\"",
                            "\"createdAt\":\"(.+?)\"",
                            "\"tripCounter\":\"(.+?)\""
                    );
            EngineRegularExpressions engineRegularExpressions =
                    new EngineRegularExpressions(
                            "\"engine\":\\{(.+?)\\}",
                            "\"volume\":\"(.+?)\"",
                            "\"brand\":\"(.+?)\""
                    );
            AutomobileParser automobileParser = new AutomobileParser();
            automobileParser.setAutomobilesListRegularExpressions(automobilesListRegularExpressions);
            automobileParser.setAutomobileRegularExpressions(automobileRegularExpressions);
            automobileParser.setEngineRegularExpressions(engineRegularExpressions);
            List<Automobile> list = automobileParser.parseAutomobilesList(path);
            list.forEach(System.out::println);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
