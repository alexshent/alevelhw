package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;

public class AutomobileBuilder {
    private String id = "";
    private String model = "";
    private BigDecimal price = BigDecimal.ZERO;
    private String bodyType = "";
    private static final int BODY_TYPE_MAX_LENGTH = 20;
    private AutomobileManufacturers manufacturer = null;

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
        if (bodyType.length() > BODY_TYPE_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "body type must be less or equal than " +
                            BODY_TYPE_MAX_LENGTH +
                            " characters"
            );
        }
        this.bodyType = bodyType;
        return this;
    }

    public AutomobileBuilder withManufacturer(AutomobileManufacturers manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public Automobile build() {
        return new Automobile(this);
    }
}
