package ua.com.alevel.alexshent.repository.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileBuilder;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;
import ua.com.alevel.alexshent.model.Engine;
import ua.com.alevel.alexshent.repository.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutomobileRepository implements Repository<Automobile> {
    private final MongoCollection<Document> collection;

    public AutomobileRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public List<Automobile> getByInvoiceId(String invoiceId) {
        Bson filter = Filters.eq("invoiceId", new ObjectId(invoiceId));
        List<Automobile> list = new ArrayList<>();
        collection.find(filter).forEach(document -> list.add(automobileObjectFromDocument(document)));
        return list;
    }

    @Override
    public Optional<Automobile> getById(String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        Document document = collection.find(filter).first();
        Automobile automobile = automobileObjectFromDocument(document);
        return Optional.of(automobile);
    }

    @Override
    public List<Optional<Automobile>> getAll() {
        List<Optional<Automobile>> list = new ArrayList<>();
        collection.find().forEach(document -> list.add(Optional.of(automobileObjectFromDocument(document))));
        return list;
    }

    @Override
    public boolean add(Automobile automobile) {
        Document document = documentFromAutomobileObject(automobile);
        InsertOneResult result = collection.insertOne(document);
        ObjectId id = result.getInsertedId().asObjectId().getValue();
        System.out.println(id);
        return true;
    }

    @Override
    public boolean add(Optional<Automobile> itemOptional) {
        Automobile automobile = itemOptional.orElse(
                new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(123.45), "BBB")
        );
        return add(automobile);
    }

    @Override
    public boolean addList(List<Automobile> items) {
        List<Document> documents = items.stream().map(this::documentFromAutomobileObject).toList();
        InsertManyResult result = collection.insertMany(documents);
        List<ObjectId> ids = new ArrayList<>();
        result.getInsertedIds().forEach((integer, bsonValue) -> ids.add(bsonValue.asObjectId().getValue()));
        ids.forEach(System.out::println);
        return true;
    }

    @Override
    public boolean update(Automobile automobile) {
        Bson filter = Filters.eq("_id", new ObjectId(automobile.getId()));
        Document document = documentFromAutomobileObject(automobile);
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

    private Document documentFromAutomobileObject(Automobile automobile) {
        Document document = new Document();
        document.append("model", automobile.getModel());
        document.append("price", automobile.getPrice());
        document.append("components", automobile.getComponents().orElse(new ArrayList<>()));
        document.append("manufacturer", automobile.getManufacturer());
        document.append("invoiceId", new ObjectId(automobile.getInvoiceId()));
        document.append("bodyType", automobile.getBodyType());
        String createdAtString = DateTimeFormatter.ISO_DATE_TIME.format(automobile.getCreatedAt());
        document.append("createdAt", createdAtString);
        document.append("tripCounter", automobile.getTripCounter());
        document.append("engine", documentFromEngineObject(automobile.getEngine()));
        return document;
    }

    private Automobile automobileObjectFromDocument(Document document) {
        AutomobileBuilder builder = new AutomobileBuilder();
        builder.withId(document.getObjectId("_id").toString());
        builder.withModel(document.getString("model"));
        org.bson.types.Decimal128 d128 = document.get("price", Decimal128.class);
        builder.withPrice(d128.bigDecimalValue());
        builder.withComponents(document.get("components", ArrayList.class));
        builder.withInvoiceId(document.getObjectId("invoiceId").toString());
        builder.withManufacturer(AutomobileManufacturers.valueOf(document.getString("manufacturer")));
        builder.withBodyType(document.getString("bodyType"));
        String createdAtString = document.getString("createdAt");
        builder.withCreatedAt(LocalDateTime.parse(createdAtString));
        builder.withTripCounter(document.getLong("tripCounter"));
        Engine engine = engineObjectFromDocument((Document) document.get("engine"));
        builder.withEngine(engine);
        return builder.build();
    }

    private Document documentFromEngineObject(Engine engine) {
        Document document = new Document();
        document.append("volume", engine.getVolume());
        document.append("brand", engine.getBrand());
        return document;
    }

    private Engine engineObjectFromDocument(Document document) {
        return new Engine(
                document.getInteger("volume"),
                document.getString("brand")
        );
    }
}
