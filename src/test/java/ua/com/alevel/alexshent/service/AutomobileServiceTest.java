package ua.com.alevel.alexshent.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.repository.AutomobileRepository;
import ua.com.alevel.alexshent.repository.Repository;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutomobileServiceTest {

    private AutomobileRepository repositoryMock;
    private AutomobileService service;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(AutomobileRepository.class);
        service = new AutomobileService(repositoryMock);
    }

    @Test
    void testRepositoryMock() {
        assertNotNull(repositoryMock);
        assertInstanceOf(AutomobileRepository.class, repositoryMock);
    }

    @Test
    void createAutos_returnsListOfExpectedSize() {
        // given
        final int numberOfAutos = 5;
        final int expected = 5;
        // when
        List<Automobile> autos = service.createAutos(numberOfAutos);
        int actual = autos.size();
        // then
        assertEquals(expected, actual);
    }

    @Test
    void createAutos_returnsNotNull() {
        // given
        final int numberOfAutos = 0;
        final int expected = 0;
        // when
        List<Automobile> autos = service.createAutos(numberOfAutos);
        // then
        assertNotNull(autos);
        int actual = autos.size();
        assertEquals(expected, actual);
    }

    /**
     * ArgumentCaptor
     */
    @Test
    void saveProducts_argumentCaptor() {
        // given
        final int numberOfAutos = 5;
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
        List<Automobile> list = service.createAutos(numberOfAutos);
        // when
        service.saveProducts(list);
        // then
        verify(repositoryMock).create(argThat((ArgumentMatcher<List<Automobile>>) autosList
                -> autosList instanceof LinkedList<Automobile> && list.size() == numberOfAutos
        ));
    }

    /**
     * throws exception
     */
    @Test
    void getProductById_ThrowException() {
        // given
        final String automobileId = "aaa-1";
        final int wantedNumberOfInvocations = 1;
        // when
        // then
        when(repositoryMock.getById(automobileId)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> service.getProductById(automobileId));
        verify(repositoryMock, times(wantedNumberOfInvocations)).getById(anyString());
        verifyNoMoreInteractions(repositoryMock);
    }
}