package ua.com.alevel.alexshent.repository.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.repository.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceRepository implements Repository<Invoice> {
    private final MongoCollection<Document> collection;
    private final BicycleRepository bicycleRepository;
    private final AutomobileRepository automobileRepository;
    private ObjectId lastId;

    public InvoiceRepository(
            MongoCollection<Document> collection,
            MongoCollection<Document> bicycleCollection,
            MongoCollection<Document> automobileCollection
    ) {
        this.collection = collection;
        bicycleRepository = new BicycleRepository(bicycleCollection);
        automobileRepository = new AutomobileRepository(automobileCollection);
    }

    public ObjectId getLastId() {
        return lastId;
    }

    private List<Vehicle> getVehiclesWithInvoiceId(String invoiceId) {
        List<Bicycle> bicycles = bicycleRepository.getByInvoiceId(invoiceId);
        List<Automobile> automobiles = automobileRepository.getByInvoiceId(invoiceId);
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.addAll(bicycles);
        vehicles.addAll(automobiles);
        return vehicles;
    }

    @Override
    public Optional<Invoice> getById(String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        Document document = collection.find(filter).first();
        Invoice invoice = objectFromDocument(document);
        invoice.setVehicles(getVehiclesWithInvoiceId(invoice.getId()));
        return Optional.of(invoice);
    }

    @Override
    public List<Optional<Invoice>> getAll() {
        List<Optional<Invoice>> list = new ArrayList<>();
        collection.find().forEach(document -> {
            Invoice invoice = objectFromDocument(document);
            invoice.setVehicles(getVehiclesWithInvoiceId(invoice.getId()));
            list.add(Optional.of(invoice));
        });
        return list;
    }

    @Override
    public boolean add(Invoice invoice) {
        // save invoice
        Document document = documentFromObject(invoice);
        InsertOneResult result = collection.insertOne(document);
        lastId = result.getInsertedId().asObjectId().getValue();
        System.out.println(lastId);
        // save vehicles
        List<Vehicle> vehicles = invoice.getVehicles();
        vehicles.forEach(vehicle -> {
            vehicle.setInvoiceId(lastId.toString());
            if (vehicle instanceof Bicycle bicycle) {
                bicycleRepository.add(bicycle);
            } else if (vehicle instanceof Automobile automobile) {
                automobileRepository.add(automobile);
            }
        });
        return true;
    }

    @Override
    public boolean add(Optional<Invoice> itemOptional) {
        Invoice invoice = itemOptional.orElse(new Invoice());
        return add(invoice);
    }

    @Override
    public boolean addList(List<Invoice> items) {
        List<Document> documents = items.stream().map(this::documentFromObject).toList();
        InsertManyResult result = collection.insertMany(documents);
        List<ObjectId> ids = new ArrayList<>();
        result.getInsertedIds().forEach((integer, bsonValue) -> ids.add(bsonValue.asObjectId().getValue()));
        ids.forEach(System.out::println);
        return true;
    }

    @Override
    public boolean update(Invoice invoice) {
        Bson filter = Filters.eq("_id", new ObjectId(invoice.getId()));
        Document document = documentFromObject(invoice);
        UpdateResult result = collection.replaceOne(filter, document);
        System.out.println(result);
        return true;
    }

    @Override
    public boolean delete(String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        DeleteResult result = collection.deleteOne(filter);
        System.out.println(result);
        return true;
    }

    private Document documentFromObject(Invoice invoice) {
        Document document = new Document();
        String createdAtString = DateTimeFormatter.ISO_DATE_TIME.format(invoice.getCreatedAt());
        document.append("createdAt", createdAtString);
        document.append("name", invoice.getName());
        return document;
    }

    private Invoice objectFromDocument(Document document) {
        InvoiceBuilder builder = new InvoiceBuilder();
        builder.withId(document.getObjectId("_id").toString());
        String createdAtString = document.getString("createdAt");
        builder.withCreatedAt(LocalDateTime.parse(createdAtString));
        builder.withName(document.getString("name"));
        return builder.build();
    }
}
