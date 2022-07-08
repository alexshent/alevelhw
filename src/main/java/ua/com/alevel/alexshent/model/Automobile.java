package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;

public class Automobile extends Vehicle {
    private String bodyType;

    public Automobile(String model, AutomobileManufacturers manufacturer, BigDecimal price, String bodyType) {
        super(model, manufacturer, price);
        this.bodyType = bodyType;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Automobile) {
            return
                    this.model.equals( ((Automobile) object).getModel() )
                    && this.price.equals( ((Automobile) object).getPrice() )
                    && this.manufacturer == ((Automobile) object).getManufacturer()
                    && this.bodyType.equals( ((Automobile) object).getBodyType() );
        }
        return false;
    }

    @Override
    public String toString() {
        return "Auto {" +
                "bodyType=" + bodyType +
                ", id=" + id +
                ", model=" + model +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }

    public static void copy(Automobile source, Automobile destination) {
        destination.setModel(source.getModel());
        destination.setPrice(source.getPrice());
        destination.setManufacturer(source.getManufacturer());
        destination.setBodyType(source.getBodyType());
    }
}
