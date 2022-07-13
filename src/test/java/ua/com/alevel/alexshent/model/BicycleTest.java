package ua.com.alevel.alexshent.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BicycleTest {

    @Test
    void testEqualsEqualObjects() {
        // given
        Bicycle bicycleA = new Bicycle("AAA", BicycleManufactures.AAA, BigDecimal.valueOf(1234.00), 2);
        Bicycle bicycleB = new Bicycle("AAA", BicycleManufactures.AAA, BigDecimal.valueOf(1234.00), 2);
        boolean expected = true;
        // when
        boolean actual = bicycleA.equals(bicycleB);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void testEqualsNotEqualObjects() {
        // given
        Bicycle bicycleA = new Bicycle("AAA", BicycleManufactures.AAA, BigDecimal.valueOf(1234.00), 2);
        Bicycle bicycleB = new Bicycle("BBB", BicycleManufactures.BBB, BigDecimal.valueOf(5678.00), 3);
        boolean expected = false;
        // when
        boolean actual = bicycleA.equals(bicycleB);
        // then
        assertEquals(expected, actual);
    }
}