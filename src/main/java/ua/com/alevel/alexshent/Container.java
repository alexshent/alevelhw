package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.Vehicle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Container <V extends Vehicle, N extends Number> {
    private V vehicle;

    public void addVehicle(V vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleClassName() {
        return vehicle.getClass().getName();
    }

    public BigDecimal getVehiclePrice() {
        return vehicle.getPrice();
    }

    public BigDecimal getDiscountPrice() {
        final int discountBottom = 10;
        final int discountTop = 30;
        Random random = new Random();
        int discount = random.nextInt(discountBottom, discountTop + 1);
        BigDecimal result = vehicle.getPrice().multiply(
                BigDecimal.valueOf((100 - discount) / 100.0)
        );
        return result.setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getExtraChargePrice(N extraCharge) {
        return  vehicle.getPrice().add(
                BigDecimal.valueOf(extraCharge.doubleValue())
        );
    }
}
