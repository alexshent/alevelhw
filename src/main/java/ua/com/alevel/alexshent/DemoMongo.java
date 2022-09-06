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
import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.repository.mongo.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DemoMongo {

    public void ping() {
        String mongoUri = Configuration.getInstance().getProperty("mongo_uri");
        String mongoDb = Configuration.getInstance().getProperty("mongo_db");
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(mongoDb);
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Connected successfully to server.");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    private void addOneBicycle(MongoCollection<Document> collection) {
        ua.com.alevel.alexshent.repository.mongo.BicycleRepository bicycleRepository =
                new ua.com.alevel.alexshent.repository.mongo.BicycleRepository(collection);
        BicycleBuilder bicycleBuilder = new BicycleBuilder();
        bicycleBuilder.withModel("Model 1");
        bicycleBuilder.withPrice(BigDecimal.valueOf(123.45));
        List<String> components = new ArrayList<>();
        components.add("Component 1");
        components.add("Component 2");
        bicycleBuilder.withComponents(components);
        bicycleBuilder.withManufacturer(BicycleManufactures.AAA);
        bicycleBuilder.withNumberOfWheels(2);
        Bicycle bicycle = bicycleBuilder.build();
        bicycleRepository.add(bicycle);
    }

    private void addThreeBicycles(MongoCollection<Document> collection) {
        ua.com.alevel.alexshent.repository.mongo.BicycleRepository bicycleRepository =
                new ua.com.alevel.alexshent.repository.mongo.BicycleRepository(collection);
        BicycleBuilder bicycleBuilder = new BicycleBuilder();

        bicycleBuilder.withModel("Model 1");
        bicycleBuilder.withPrice(BigDecimal.valueOf(123.45));
        List<String> components = new ArrayList<>();
        components.add("Component 1");
        components.add("Component 2");
        bicycleBuilder.withComponents(components);
        bicycleBuilder.withManufacturer(BicycleManufactures.AAA);
        bicycleBuilder.withNumberOfWheels(2);
        Bicycle bicycle1 = bicycleBuilder.build();

        bicycleBuilder.withModel("Model 2");
        bicycleBuilder.withPrice(BigDecimal.valueOf(123.45));
        components.clear();
        components.add("Component 1");
        components.add("Component 2");
        bicycleBuilder.withComponents(components);
        bicycleBuilder.withManufacturer(BicycleManufactures.AAA);
        bicycleBuilder.withNumberOfWheels(2);
        Bicycle bicycle2 = bicycleBuilder.build();

        bicycleBuilder.withModel("Model 3");
        bicycleBuilder.withPrice(BigDecimal.valueOf(123.45));
        components.clear();
        components.add("Component 1");
        components.add("Component 2");
        bicycleBuilder.withComponents(components);
        bicycleBuilder.withManufacturer(BicycleManufactures.AAA);
        bicycleBuilder.withNumberOfWheels(2);
        Bicycle bicycle3 = bicycleBuilder.build();

        List<Bicycle> list = new ArrayList<>();
        list.add(bicycle1);
        list.add(bicycle2);
        list.add(bicycle3);

        bicycleRepository.addList(list);
    }

    private void deleteOneBicycle(MongoCollection<Document> collection, String id) {
        ua.com.alevel.alexshent.repository.mongo.BicycleRepository bicycleRepository =
                new ua.com.alevel.alexshent.repository.mongo.BicycleRepository(collection);
        bicycleRepository.delete(id);
    }

    private void updateOneBicycle(MongoCollection<Document> collection, String id) {
        ua.com.alevel.alexshent.repository.mongo.BicycleRepository bicycleRepository =
                new ua.com.alevel.alexshent.repository.mongo.BicycleRepository(collection);
        Optional<Bicycle> optional = bicycleRepository.getById(id);
        Bicycle bicycle = optional.get();
        bicycle.setNumberOfWheels(4);
        bicycleRepository.update(bicycle);
    }

    private void getOneBicycle(MongoCollection<Document> collection, String id) {
        ua.com.alevel.alexshent.repository.mongo.BicycleRepository bicycleRepository =
                new ua.com.alevel.alexshent.repository.mongo.BicycleRepository(collection);
        Optional<Bicycle> optional = bicycleRepository.getById(id);
        System.out.println(optional);
    }

    private void getAllBicycles(MongoCollection<Document> collection) {
        ua.com.alevel.alexshent.repository.mongo.BicycleRepository bicycleRepository =
                new ua.com.alevel.alexshent.repository.mongo.BicycleRepository(collection);
        List<Optional<Bicycle>> list = bicycleRepository.getAll();
        System.out.println(list);
    }

    // =============================

    private void addOneAutomobile(MongoCollection<Document> collection) {
        ua.com.alevel.alexshent.repository.mongo.AutomobileRepository automobileRepository =
                new ua.com.alevel.alexshent.repository.mongo.AutomobileRepository(collection);
        AutomobileBuilder automobileBuilder = new AutomobileBuilder();
        automobileBuilder.withModel("Model 1");
        automobileBuilder.withPrice(BigDecimal.valueOf(123.45));
        List<String> components = new ArrayList<>();
        components.add("Component 1");
        components.add("Component 2");
        automobileBuilder.withComponents(components);
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withBodyType("BBB");
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withTripCounter(111);
        Engine engine = new Engine(55, "Brand 1");
        automobileBuilder.withEngine(engine);
        Automobile automobile = automobileBuilder.build();
        automobileRepository.add(automobile);
    }

    private void addThreeAutomobiles(MongoCollection<Document> collection) {
        ua.com.alevel.alexshent.repository.mongo.AutomobileRepository automobileRepository =
                new ua.com.alevel.alexshent.repository.mongo.AutomobileRepository(collection);
        AutomobileBuilder automobileBuilder = new AutomobileBuilder();

        automobileBuilder.withModel("Model 1");
        automobileBuilder.withPrice(BigDecimal.valueOf(123.45));
        List<String> components = new ArrayList<>();
        components.add("Component 1");
        components.add("Component 2");
        automobileBuilder.withComponents(components);
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withBodyType("BBB");
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withTripCounter(111);
        automobileBuilder.withEngine(new Engine(55, "Brand 1"));
        Automobile automobile1 = automobileBuilder.build();

        automobileBuilder.withModel("Model 2");
        automobileBuilder.withPrice(BigDecimal.valueOf(123.45));
        components.clear();
        components.add("Component 1");
        components.add("Component 2");
        automobileBuilder.withComponents(components);
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withBodyType("BBB");
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withTripCounter(111);
        automobileBuilder.withEngine(new Engine(55, "Brand 1"));
        Automobile automobile2 = automobileBuilder.build();

        automobileBuilder.withModel("Model 3");
        automobileBuilder.withPrice(BigDecimal.valueOf(123.45));
        components.clear();
        components.add("Component 1");
        components.add("Component 2");
        automobileBuilder.withComponents(components);
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withBodyType("BBB");
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withTripCounter(111);
        automobileBuilder.withEngine(new Engine(55, "Brand 1"));
        Automobile automobile3 = automobileBuilder.build();

        List<Automobile> list = new ArrayList<>();
        list.add(automobile1);
        list.add(automobile2);
        list.add(automobile3);

        automobileRepository.addList(list);
    }

    private void getOneAutomobile(MongoCollection<Document> collection, String id) {
        ua.com.alevel.alexshent.repository.mongo.AutomobileRepository automobileRepository =
                new ua.com.alevel.alexshent.repository.mongo.AutomobileRepository(collection);
        Optional<Automobile> optional = automobileRepository.getById(id);
        System.out.println(optional);
    }

    private void getAllAutomobiles(MongoCollection<Document> collection) {
        ua.com.alevel.alexshent.repository.mongo.AutomobileRepository automobileRepository =
                new ua.com.alevel.alexshent.repository.mongo.AutomobileRepository(collection);
        List<Optional<Automobile>> list = automobileRepository.getAll();
        System.out.println(list);
    }

    private void deleteOneAutomobile(MongoCollection<Document> collection, String id) {
        ua.com.alevel.alexshent.repository.mongo.AutomobileRepository automobileRepository =
                new ua.com.alevel.alexshent.repository.mongo.AutomobileRepository(collection);
        automobileRepository.delete(id);
    }

    private void updateOneAutomobile(MongoCollection<Document> collection, String id) {
        ua.com.alevel.alexshent.repository.mongo.AutomobileRepository automobileRepository =
                new ua.com.alevel.alexshent.repository.mongo.AutomobileRepository(collection);
        Automobile automobile = automobileRepository.getById(id).get();
        automobile.setBodyType("QWERTY");
        automobileRepository.update(automobile);
    }

    public void addOneInvoiceWithTwoAutomobilesAndOneBicycle(
            MongoCollection<Document> collectionBicycles,
            MongoCollection<Document> collectionAutomobiles,
            MongoCollection<Document> collectionInvoices
    ) {
        InvoiceRepository invoiceRepository = new InvoiceRepository(collectionInvoices, collectionBicycles, collectionAutomobiles);

        List<String> components = new ArrayList<>();
        InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
        AutomobileBuilder automobileBuilder = new AutomobileBuilder();
        BicycleBuilder bicycleBuilder = new BicycleBuilder();

        automobileBuilder.withModel("M1");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B1");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(11, "Brand 1"));
        automobileBuilder.withTripCounter(1);
        components.add("component 1");
        components.add("component 2");
        components.add("component 3");
        automobileBuilder.withComponents(components);
        Automobile automobile1 = automobileBuilder.build();

        automobileBuilder.withModel("M2");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B2");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(22, "Brand 2"));
        automobileBuilder.withTripCounter(2);
        components.clear();
        components.add("component 4");
        components.add("component 5");
        components.add("component 6");
        automobileBuilder.withComponents(components);
        Automobile automobile2 = automobileBuilder.build();

        bicycleBuilder.withModel("M-b1");
        bicycleBuilder.withPrice(BigDecimal.valueOf(12.00));
        bicycleBuilder.withManufacturer(BicycleManufactures.AAA);
        components.clear();
        components.add("component 7");
        components.add("component 8");
        bicycleBuilder.withComponents(components);
        bicycleBuilder.withNumberOfWheels(2);
        Bicycle bicycle1 = bicycleBuilder.build();

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(automobile1);
        vehicles.add(automobile2);
        vehicles.add(bicycle1);

        invoiceBuilder.withCreatedAt(LocalDateTime.now());
        invoiceBuilder.withName("Invoice 1");
        invoiceBuilder.withVehicles(vehicles);
        Invoice invoice1 = invoiceBuilder.build();

        invoiceRepository.add(invoice1);
    }

    private void getOneInvoice(
            MongoCollection<Document> collectionBicycles,
            MongoCollection<Document> collectionAutomobiles,
            MongoCollection<Document> collectionInvoices,
            String id
    ) {
        InvoiceRepository invoiceRepository = new InvoiceRepository(collectionInvoices, collectionBicycles, collectionAutomobiles);
        Optional<Invoice> invoice = invoiceRepository.getById(id);
        System.out.println(invoice);
    }

    public void demo() {
        String mongoUri = Configuration.getInstance().getProperty("mongo_uri");
        String mongoDb = Configuration.getInstance().getProperty("mongo_db");
        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(mongoDb);
            MongoCollection<Document> collectionBicycles = database.getCollection("bicycles");
            MongoCollection<Document> collectionAutomobiles = database.getCollection("automobiles");
            MongoCollection<Document> collectionInvoices = database.getCollection("invoices");

            //addOneBicycle(collectionBicycles);
            //addThreeBicycles(collectionBicycles);
            //deleteOneBicycle(collectionBicycles, "631436edd84694020214c54d");
            //getOneBicycle(collectionBicycles, "631436edd84694020214c54e");
            //updateOneBicycle(collectionBicycles, "631436edd84694020214c54e");
            //getAllBicycles(collectionBicycles);

            //addOneAutomobile(collectionAutomobiles);
            //addThreeAutomobiles(collectionAutomobiles);
            //deleteOneAutomobile(collectionAutomobiles, "63149922b3c67e427817f061");
            //getOneAutomobile(collectionAutomobiles, "6314953a2d463d400dfba975");
            //updateOneAutomobile(collectionAutomobiles, "63149922b3c67e427817f060");
            //getAllAutomobiles(collectionAutomobiles);

            //addOneInvoiceWithTwoAutomobilesAndOneBicycle(collectionBicycles, collectionAutomobiles, collectionInvoices);
            //getOneInvoice(collectionBicycles, collectionAutomobiles, collectionInvoices,"6314f121f6f5887741b8065f");
        }
    }
}
