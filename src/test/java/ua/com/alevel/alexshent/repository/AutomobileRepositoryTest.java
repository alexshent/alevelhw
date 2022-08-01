package ua.com.alevel.alexshent.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<Automobile> automobileFromRepository = repository.getById(automobileId);
        // then
        assertTrue(automobileFromRepository.isEmpty());
    }

    @Test
    void getById_repositoryIsNotEmpty() {
        // given
        // when
        Automobile automobile = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(12345.67), "BBB");
        String automobileId = automobile.getId();
        repository.add(automobile);
        Optional<Automobile> automobileFromRepository = repository.getById(automobileId);
        // then
        assertFalse(automobileFromRepository.isEmpty());
        assertSame(automobile, automobileFromRepository.get());
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
        spy.add(automobile);
        Optional<Automobile> automobileFromRepository = spy.getById(automobileId);
        // then
        automobileFromRepository.ifPresent(value -> assertEquals(automobile.getPrice(), value.getPrice()));
    }

    @Test
    void update_objectIsInRepository() {
        // given
        // when
        Automobile automobile1 = new Automobile("AAA1", AutomobileManufacturers.BMW, BigDecimal.valueOf(1111.00), "BBB1");
        repository.add(automobile1);
        Automobile automobile2 = new Automobile("AAA2", AutomobileManufacturers.BMW, BigDecimal.valueOf(2222.00), "BBB2");
        automobile2.setId(automobile1.getId());
        boolean actual = repository.update(automobile2);
        // then
        assertTrue(actual);
    }

    @Test
    void update_objectIsNotInRepository() {
        // given
        // when
        Automobile automobile = new Automobile("AAA1", AutomobileManufacturers.BMW, BigDecimal.valueOf(1111.00), "BBB1");
        boolean actual = repository.update(automobile);
        // then
        assertFalse(actual);
    }

    @Test
    void getAllNoEmpty_replaceEmptyValues() {
        Automobile automobile1 = new Automobile("AAA1", AutomobileManufacturers.BMW, BigDecimal.valueOf(1111.00), "BBB1");
        Automobile automobile2 = new Automobile("AAA2", AutomobileManufacturers.BMW, BigDecimal.valueOf(1111.00), "BBB2");
        Automobile automobile3 = new Automobile("AAA3", AutomobileManufacturers.BMW, BigDecimal.valueOf(1111.00), "BBB3");
        repository.add(automobile1);
        repository.add(automobile2);
        repository.add(automobile3);
        List<Optional<Automobile>> automobiles = repository.getAllNoEmpty();
        for (Optional<Automobile> autoOptional : automobiles) {
            assertFalse(autoOptional.isEmpty());
        }
    }

    @Test
    void getAllNoEmpty_emptyValues() {
        repository.add((Automobile) null);
        repository.add((Automobile) null);
        repository.add((Automobile) null);
        List<Optional<Automobile>> automobiles = repository.getAll();
        for (Optional<Automobile> autoOptional : automobiles) {
            assertTrue(autoOptional.isEmpty());
        }
    }

    @Test
    void add_optionalIsNotEmpty() {
        Optional<Automobile> automobileOptional = Optional.of(
                new Automobile("AAA1", AutomobileManufacturers.BMW, BigDecimal.valueOf(1111.00), "BBB1")
        );
        boolean actual = repository.add(automobileOptional);
        assertTrue(actual);
    }

    @Test
    void add_optionalIsEmpty() {
        Optional<Automobile> automobileOptional = Optional.empty();
        assertThrows(NoSuchElementException.class, () ->
                repository.add(automobileOptional)
        );
    }
}