package ua.com.alevel.alexshent.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileTest {

    @Test
    void testEqualsEqualObjects() {
        // given
        Automobile automobileA = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(1234.00), "Body 1");
        Automobile automobileB = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(1234.00), "Body 1");
        boolean expected = true;
        // when
        boolean actual = automobileA.equals(automobileB);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void testEqualsNotEqualObjects() {
        // given
        Automobile automobileA = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(1234.00), "Body 1");
        Automobile automobileB = new Automobile("BBB", AutomobileManufacturers.OPEL, BigDecimal.valueOf(2345.00), "Body 2");
        boolean expected = false;
        // when
        boolean actual = automobileA.equals(automobileB);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void testMocking() {
        Automobile automobileMock = Mockito.mock(Automobile.class);
        Mockito.when(automobileMock.getBodyType()).thenReturn("zzz");
        Mockito.when(automobileMock.toString()).thenCallRealMethod();
        Mockito.when(automobileMock.getPrice()).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, automobileMock::getPrice);
    }
}