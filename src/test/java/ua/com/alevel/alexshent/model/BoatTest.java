package ua.com.alevel.alexshent.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {

    @Test
    void testEqualsEqualObjects() {
        // given
        Boat boatA = new Boat("AAA", BoatManufactures.EEE, BigDecimal.valueOf(12345.00), true);
        Boat boatB = new Boat("AAA", BoatManufactures.EEE, BigDecimal.valueOf(12345.00), true);
        boolean expected = true;
        // when
        boolean actual = boatA.equals(boatB);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void testEqualsNotEqualObjects() {
        // given
        Boat boatA = new Boat("AAA", BoatManufactures.EEE, BigDecimal.valueOf(12345.00), true);
        Boat boatB = new Boat("BBB", BoatManufactures.FFF, BigDecimal.valueOf(23456.00), false);
        boolean expected = false;
        // when
        boolean actual = boatA.equals(boatB);
        // then
        assertEquals(expected, actual);
    }
}