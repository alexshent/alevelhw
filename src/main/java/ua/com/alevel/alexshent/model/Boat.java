package ua.com.alevel.alexshent.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Boat extends Vehicle {
    private BoatManufactures manufacturer;
    private boolean withEngine;

    public Boat(String model, BoatManufactures manufacturer, BigDecimal price, boolean withEngine) {
        super(model, price);
        this.manufacturer = manufacturer;
        this.withEngine = withEngine;
    }

    public BoatManufactures getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(BoatManufactures manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isWithEngine() {
        return withEngine;
    }

    public void setWithEngine(boolean withEngine) {
        this.withEngine = withEngine;
    }

    public static void copy(Boat source, Boat destination) {
        destination.setModel(source.getModel());
        destination.setPrice(source.getPrice());
        destination.setManufacturer(source.getManufacturer());
        destination.setWithEngine(source.isWithEngine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, withEngine);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Boat) {
            return
                    Objects.equals(model, ((Boat) object).getModel())
                            && Objects.equals(price, ((Boat) object).getPrice())
                            && this.manufacturer == ((Boat) object).getManufacturer()
                            && this.withEngine == ((Boat) object).isWithEngine();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Boat {" +
                "withEngine=" + withEngine +
                ", id=" + id +
                ", model=" + model +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
