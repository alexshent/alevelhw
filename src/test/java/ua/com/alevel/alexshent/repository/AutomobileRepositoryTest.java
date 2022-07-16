package ua.com.alevel.alexshent.repository;

import org.junit.jupiter.api.Test;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutomobileRepositoryTest {

    @Test
    void getById_repositoryIsEmpty() {
        // given
        final String automobileId = "a1";
        // when
        AutomobileRepository repository = new AutomobileRepository();
        Automobile automobileFromRepository = repository.getById(automobileId);
        // then
        assertNull(automobileFromRepository);
    }

    @Test
    void getById_repositoryIsNotEmpty() {
        // given
        final int wantedNumberOfInvocations = 1;
        final String automobileId = "a1";
        // when
        Automobile automobileMock = mock(Automobile.class);
        when(automobileMock.getId()).thenReturn(automobileId);
        AutomobileRepository repository = new AutomobileRepository();
        repository.create(automobileMock);
        Automobile automobileFromRepository = repository.getById(automobileId);
        // then
        assertNotNull(automobileFromRepository);
        assertSame(automobileFromRepository, automobileMock);
        verify(automobileMock, times(wantedNumberOfInvocations)).getId();
        verify(automobileMock, never()).getBodyType();
        verifyNoMoreInteractions(automobileMock);
    }

    /**
     * calls real method
     */
    @Test
    void getById_repositoryIsNotEmpty_Spy() {
        // given
        // when
        AutomobileRepository repository = new AutomobileRepository();
        AutomobileRepository spy = spy(repository);
        Automobile automobile = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(12345.67), "BBB");
        String automobileId = automobile.getId();
        spy.create(automobile);
        Automobile automobileFromRepository = spy.getById(automobileId);
        // then
        assertEquals(automobile.getPrice(), automobileFromRepository.getPrice());
    }

    /**
     * throws exception
     */
    @Test
    void getById_repositoryIsNotEmpty_Exception() {
        AutomobileRepository repository = new AutomobileRepository();
        Automobile automobileMock = mock(Automobile.class);
        when(automobileMock.getId()).thenThrow(new RuntimeException());
        repository.create(automobileMock);
        assertThrows(RuntimeException.class, () -> {
            repository.getById("a1");
        });
    }
}