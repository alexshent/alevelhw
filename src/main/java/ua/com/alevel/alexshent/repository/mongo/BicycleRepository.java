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
import ua.com.alevel.alexshent.model.Bicycle;
import ua.com.alevel.alexshent.model.BicycleBuilder;
import ua.com.alevel.alexshent.model.BicycleManufactures;
import ua.com.alevel.alexshent.repository.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BicycleRepository implements Repository<Bicycle> {

    private final MongoCollection<Document> collection;

    public BicycleRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public List<Bicycle> getByInvoiceId(String invoiceId) {
        Bson filter = Filters.eq("invoiceId", new ObjectId(invoiceId));
        List<Bicycle> list = new ArrayList<>();
        collection.find(filter).forEach(document -> list.add(objectFromDocument(document)));
        return list;
    }

    @Override
    public Optional<Bicycle> getById(String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        Document document = collection.find(filter).first();
        Bicycle bicycle = objectFromDocument(document);
        return Optional.of(bicycle);
    }

    @Override
    public List<Optional<Bicycle>> getAll() {
        List<Optional<Bicycle>> list = new ArrayList<>();
        collection.find().forEach(document -> list.add(Optional.of(objectFromDocument(document))));
        return list;
    }

    @Override
    public boolean add(Bicycle bicycle) {
        Document document = documentFromObject(bicycle);
        InsertOneResult result = collection.insertOne(document);
        ObjectId id = result.getInsertedId().asObjectId().getValue();
        System.out.println(id);
        return true;
    }

    @Override
    public boolean add(Optional<Bicycle> itemOptional) {
        Bicycle bicycle = itemOptional.orElse(new Bicycle("model", BicycleManufactures.AAA, BigDecimal.ZERO, 2));
        return add(bicycle);
    }

    @Override
    public boolean addList(List<Bicycle> items) {
        List<Document> documents = items.stream().map(this::documentFromObject).toList();
        InsertManyResult result = collection.insertMany(documents);
        List<ObjectId> ids = new ArrayList<>();
        result.getInsertedIds().forEach((integer, bsonValue) -> ids.add(bsonValue.asObjectId().getValue()));
        ids.forEach(System.out::println);
        return true;
    }

    @Override
    public boolean update(Bicycle bicycle) {
        Bson filter = Filters.eq("_id", new ObjectId(bicycle.getId()));
        Document document = documentFromObject(bicycle);
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

    private Document documentFromObject(Bicycle bicycle) {
        Document document = new Document();
        document.append("model", bicycle.getModel());
        document.append("price", bicycle.getPrice());
        document.append("components", bicycle.getComponents().orElse(new ArrayList<>()));
        document.append("invoiceId", new ObjectId(bicycle.getInvoiceId()));
        document.append("manufacturer", bicycle.getManufacturer());
        document.append("numberOfWheels", bicycle.getNumberOfWheels());
        return document;
    }

    private Bicycle objectFromDocument(Document document) {
        BicycleBuilder builder = new BicycleBuilder();
        builder.withId(document.getObjectId("_id").toString());
        builder.withModel(document.getString("model"));
        org.bson.types.Decimal128 d128 = document.get("price", Decimal128.class);
        builder.withPrice(d128.bigDecimalValue());
        builder.withComponents(document.get("components", ArrayList.class));
        builder.withInvoiceId(document.getObjectId("invoiceId").toString());
        builder.withManufacturer(BicycleManufactures.valueOf(document.getString("manufacturer")));
        builder.withNumberOfWheels(document.getInteger("numberOfWheels"));
        return builder.build();
    }
}
