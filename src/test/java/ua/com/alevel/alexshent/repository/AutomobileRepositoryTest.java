package ua.com.alevel.alexshent.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutomobileRepositoryTest {

    private AutomobileRepository repository;

    @BeforeEach
    void setUp() {
        repository = new AutomobileRepository();
    }

    @Test
    void getById_repositoryIsEmpty() {
        // given
        final String automobileId = "a1";
        // when
        Automobile automobileFromRepository = repository.getById(automobileId);
        // then
        assertNull(automobileFromRepository);
    }

    @Test
    void getById_repositoryIsNotEmpty() {
        // given
        // when
        Automobile automobile = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(12345.67), "BBB");
        String automobileId = automobile.getId();
        repository.create(automobile);
        Automobile automobileFromRepository = repository.getById(automobileId);
        // then
        assertNotNull(automobileFromRepository);
        assertSame(automobileFromRepository, automobile);
    }

    /**
     * calls real method
     */
    @Test
    void getById_repositoryIsNotEmpty_Spy() {
        // given
        // when
        AutomobileRepository spy = spy(repository);
        Automobile automobile = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(12345.67), "BBB");
        String automobileId = automobile.getId();
        spy.create(automobile);
        Automobile automobileFromRepository = spy.getById(automobileId);
        // then
        assertEquals(automobile.getPrice(), automobileFromRepository.getPrice());
    }
}