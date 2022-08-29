package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AutomobileBuilder {
    private String id;
    private String model;
    private BigDecimal price;
    private List<String> components;
    private String invoiceId;
    private String bodyType = "";
    private static final int BODY_TYPE_MAX_LENGTH = 20;
    private AutomobileManufacturers manufacturer;
    private LocalDateTime createdAt;
    private long tripCounter;
    private Engine engine;

    private void checkBodyType() {
        if (bodyType.length() > BODY_TYPE_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "body type must be less or equal than " +
                            BODY_TYPE_MAX_LENGTH +
                            " characters"
            );
        }
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getBodyType() {
        return bodyType;
    }

    public AutomobileManufacturers getManufacturer() {
        return manufacturer;
    }

    public List<String> getComponents() {
        return components;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getTripCounter() {
        return tripCounter;
    }

    public Engine getEngine() {
        return engine;
    }

    public AutomobileBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public AutomobileBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public AutomobileBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public AutomobileBuilder withBodyType(String bodyType) {
        checkBodyType();
        this.bodyType = bodyType;
        return this;
    }

    public AutomobileBuilder withManufacturer(AutomobileManufacturers manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public AutomobileBuilder withComponents(List<String> components) {
        this.components = components;
        return this;
    }

    public AutomobileBuilder withInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public AutomobileBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AutomobileBuilder withTripCounter(long tripCounter) {
        this.tripCounter = tripCounter;
        return this;
    }

    public AutomobileBuilder withEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public Automobile build() {
        checkBodyType();
        return new Automobile(this);
    }
}
