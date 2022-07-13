package ua.com.alevel.alexshent.service;

import org.junit.jupiter.api.Test;
import ua.com.alevel.alexshent.model.Automobile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutomobileServiceTest {

    @Test
    void saveProducts() {
        // given
        int expectedListSize = 5;
        int numberOfAutos = 5;
        // when
        AutomobileService service = new AutomobileService();
        List<Automobile> autos = service.createAutos(numberOfAutos);
        int actualListSize = autos.size();
        service.saveProducts(autos);
        // then
        assertEquals(expectedListSize, actualListSize);
    }
}