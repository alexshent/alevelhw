package ua.com.alevel.alexshent;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import ua.com.alevel.alexshent.command.CommandExecutor;
import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.reader.AutomobilesReader;
import ua.com.alevel.alexshent.repository.BoatRepository;
import ua.com.alevel.alexshent.repository.database.AutomobileRepository;
import ua.com.alevel.alexshent.repository.database.BicycleRepository;
import ua.com.alevel.alexshent.repository.database.InvoiceRepository;
import ua.com.alevel.alexshent.service.AutomobileService;
import ua.com.alevel.alexshent.service.BicycleService;
import ua.com.alevel.alexshent.service.BoatService;
import ua.com.alevel.alexshent.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Boat boat = boatService.getProductById(targetBoatId).orElseThrow();
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

    public void readObjectsFromFiles() {
        AutomobilesReader automobilesReader = new AutomobilesReader();

        // xml
        try {
            String resourceFileName = "automobiles.xml";
            Path path = Path.of(ClassLoader.getSystemResource(resourceFileName).toURI());
            List<Automobile> list = automobilesReader.readXmlList(path);
            list.forEach(System.out::println);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // json
        try {
            String resourceFileName = "automobiles.json";
            Path path = Path.of(ClassLoader.getSystemResource(resourceFileName).toURI());
            List<Automobile> list = automobilesReader.readJsonList(path);
            list.forEach(System.out::println);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void useStreams() {
        List<Automobile> list = autoService.createAutos(10);
        final double priceX = 333.00;

        // Найти машины дороже цены Х и показать их наименование
        Stream<Automobile> stream = list.stream();
        stream
                .filter(automobile -> automobile.getPrice().compareTo(BigDecimal.valueOf(priceX)) > 0)
                .forEach(System.out::println);

        // Посчитать сумму машин через reduce
        stream = list.stream();
        BigDecimal sum =
                stream
                        .map(Vehicle::getPrice)
                        .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
        System.out.println("sum = " + sum);

        // Отсортировать машины по названию, убрать дубликаты, преобразовать в - Map где ключ это id машины, а значение это его тип
        stream = list.stream();
        stream
                .sorted(Comparator.comparing(Vehicle::getModel))
                .distinct()
                .collect(Collectors.toMap(Vehicle::getId, Automobile::getBodyType))
                .forEach((k, v) -> System.out.println(k + ":" + v));

        // Добавить в одну машину коллекцию деталей (например List<String> details), проверить среди всех машин есть ли наличие конкретной детали
        final List<String> components = new ArrayList<>();
        components.add("AAA");
        components.add("BBB");
        components.add("CCC");
        list.get(0).setComponents(components);
        stream = list.stream();
        boolean contains = stream.allMatch(automobile -> automobile.getComponents().isPresent() && automobile.getComponents().get().contains("BBB"));
        System.out.println(contains);

        // Получить статистику по цене всех машин
        stream = list.stream();
        stream.collect(Collectors.groupingBy(Vehicle::getPrice, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + ":" + v));

        // Написать реализацию предиката который проверяет что в переданной коллекции у всех машин есть цена.
        stream = list.stream();
        Predicate<Vehicle> predicate = vehicle -> Objects.nonNull(vehicle.getPrice());
        boolean allAutomobilesHavePrice = stream.allMatch(predicate);
        System.out.println(allAutomobilesHavePrice);

        // Написать реализацию Function которая принимает Map<String, Object> и создает конкретную машину на основании полей Map
        final String modelField = "model";
        final String manufacturerField = "manufacturer";
        final String priceField = "price";
        final String bodyTypeField = "bodyType";

        List<Map<String, Object>> listOfMaps = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put(modelField, "AAA1");
        map1.put(manufacturerField, AutomobileManufacturers.BMW);
        map1.put(priceField, BigDecimal.valueOf(1234.00));
        map1.put(bodyTypeField, "BBB1");
        listOfMaps.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put(modelField, "AAA2");
        map2.put(manufacturerField, AutomobileManufacturers.BMW);
        map2.put(priceField, BigDecimal.valueOf(1234.00));
        map2.put(bodyTypeField, "BBB2");
        listOfMaps.add(map2);

        Function<Map<String, Object>, Vehicle> function = map -> new Automobile(
                (String) map.get(modelField),
                (AutomobileManufacturers) map.get(manufacturerField),
                (BigDecimal) map.get(priceField),
                (String) map.get(bodyTypeField)
        );
        listOfMaps.stream().map(function).forEach(System.out::println);
    }

    public void useMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Service<Automobile> service = new AutomobileService();
        CommandExecutor commandExecutor = new CommandExecutor(service);
        int option;
        do {
            System.out.println(commandExecutor.getOptionsMenu());
            try {
                String userInput = reader.readLine();
                option = Integer.parseInt(userInput);
                commandExecutor.executeCommand(option);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (option != 0);
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

    public void useAutomobileBuilder() {
        AutomobileBuilder automobileBuilder = new AutomobileBuilder();
        automobileBuilder.withId("AAA123");
        automobileBuilder.withModel("AAA");
        automobileBuilder.withPrice(BigDecimal.valueOf(12345.00));
        automobileBuilder.withBodyType("BBB");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        Automobile automobile = automobileBuilder.build();
        System.out.println(automobile.toString());
    }

    public void useJDBC() {
        JDBC.getConnection();

        //useJDBC_addOneAutomobile();
        //useJDBC_addThreeAutomobiles();
        //useJDBC_getAllAutomobiles();
        //useJDBC_getOneAutomobile();
        //useJDBC_updateOneAutomobile();
        //useJDBC_updateAllAutomobiles();
        //useJDBC_addOneInvoiceWithTwoAutomobilesAndOneBicycle();
        //useJDBC_getOneInvoice();
        //useJDBC_getAllInvoices();
        //useJDBC_invoiceRepository();

        JDBC.closeConnection();
    }

    public void useAnnotations() {
        Reflector reflector = new Reflector("ua\\.com[\\.a-z]+");
        Injector injector = new Injector(reflector);
        injector.run();

        BoatRepository boatRepository = (BoatRepository) injector.getInstance(BoatRepository.class, null);
        BoatService boatService = (BoatService) injector.getInstance(BoatService.class, BoatRepository.class);
        final int boatsNumber = 5;
        boatService.saveProducts(boatService.createBoats(boatsNumber));
        boatService.printAll();
    }
}
