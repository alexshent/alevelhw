package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;
import java.util.List;

public class BicycleBuilder {
    private String id;
    private String model;
    private BigDecimal price;
    private List<String> components;
    private BicycleManufactures manufacturer;
    private int numberOfWheels;
    private String invoiceId;

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<String> getComponents() {
        return components;
    }

    public BicycleManufactures getManufacturer() {
        return manufacturer;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public BicycleBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public BicycleBuilder withModel(String model) {
        this.model = model;
        return this;
    }

    public BicycleBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BicycleBuilder withComponents(List<String> components) {
        this.components = components;
        return this;
    }

    public BicycleBuilder withManufacturer(BicycleManufactures manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public BicycleBuilder withNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
        return this;
    }

    public BicycleBuilder withInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public Bicycle build() {
        return new Bicycle(this);
    }
}
