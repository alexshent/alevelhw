package ua.com.alevel.alexshent.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;
import ua.com.alevel.alexshent.repository.AutomobileRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutomobileServiceTest {

    @Test
    void deleteProduct_validId() {
        // given
        final int numberOfAutos = 5;
        final boolean expectedDeleteResult = true;
        // when
        AutomobileService service = new AutomobileService();
        List<Automobile> autos = service.createAutos(numberOfAutos);
        String targetAutoId = autos.get(0).getId();
        service.saveProducts(autos);
        boolean actualDeleteResult = service.deleteProduct(targetAutoId);
        Automobile automobileFromService = service.getProductById(targetAutoId);
        // then
        assertEquals(expectedDeleteResult, actualDeleteResult);
        assertNull(automobileFromService);
    }

    @Test
    void deleteProduct_invalidId() {
        // given
        final int numberOfAutos = 5;
        final boolean expectedDeleteResult = false;
        final String invalidId = "a12345";
        // when
        AutomobileService service = new AutomobileService();
        List<Automobile> autos = service.createAutos(numberOfAutos);
        service.saveProducts(autos);
        boolean actualDeleteResult = service.deleteProduct(invalidId);
        Automobile automobileFromService = service.getProductById(invalidId);
        // then
        assertEquals(expectedDeleteResult, actualDeleteResult);
        assertNull(automobileFromService);
    }

    @Test
    void updateProduct() {
        // given
        final AutomobileManufacturers firstManufacturer = AutomobileManufacturers.OPEL;
        final AutomobileManufacturers secondManufacturer = AutomobileManufacturers.BMW;
        final boolean expectedUpdateResult = true;
        final AutomobileManufacturers expectedFirstManufacturer = AutomobileManufacturers.OPEL;
        final AutomobileManufacturers expectedSecondManufacturer = AutomobileManufacturers.BMW;
        // when
        Automobile automobile = new Automobile("AAA", firstManufacturer, BigDecimal.valueOf(12345.67), "BBB");
        List<Automobile> list = new ArrayList<>();
        list.add(automobile);
        AutomobileService service = new AutomobileService();
        service.saveProducts(list);
        Automobile automobileFromService = service.getProductById(automobile.getId());
        AutomobileManufacturers actualFirstManufacturer = automobileFromService.getManufacturer();
        automobile.setManufacturer(secondManufacturer);
        boolean actualUpdateResult = service.updateProduct(automobile);
        automobileFromService = service.getProductById(automobile.getId());
        AutomobileManufacturers actualSecondManufacturer = automobileFromService.getManufacturer();
        // then
        assertEquals(expectedUpdateResult, actualUpdateResult);
        assertEquals(expectedFirstManufacturer, actualFirstManufacturer);
        assertEquals(expectedSecondManufacturer, actualSecondManufacturer);
    }

    /**
     * ArgumentCaptor
     */
    @Test
    void saveProducts_argumentCaptor() {
        // given
        final int numberOfAutos = 5;
        AutomobileRepository repositoryMock = mock(AutomobileRepository.class);
        AutomobileService service = new AutomobileService(repositoryMock);
        List<Automobile> list = service.createAutos(numberOfAutos);
        // when
        service.saveProducts(list);
        // then
        ArgumentCaptor<List<Automobile>> listArgumentCaptor = ArgumentCaptor.forClass((Class) List.class);
        verify(repositoryMock).create(listArgumentCaptor.capture());
        List<Automobile> actualList = listArgumentCaptor.getValue();
        assertSame(list, actualList);
    }

    /**
     * ArgumentMatcher
     */
    @Test
    void saveProducts_argumentMatcher() {
        // given
        final int numberOfAutos = 5;
        AutomobileRepository repositoryMock = mock(AutomobileRepository.class);
        AutomobileService service = new AutomobileService(repositoryMock);
        List<Automobile> list = service.createAutos(numberOfAutos);
        // when
        service.saveProducts(list);
        // then
        verify(repositoryMock).create(argThat((ArgumentMatcher<List<Automobile>>) autosList -> {
            return autosList instanceof LinkedList<Automobile> && list.size() == numberOfAutos;
        }));
    }
}