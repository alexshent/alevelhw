package ua.com.alevel.alexshent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;
import ua.com.alevel.alexshent.repository.AutomobileRepository;
import ua.com.alevel.alexshent.repository.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class AutomobileService extends Service<Automobile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutomobileService.class);

    public AutomobileService() {
        repository = new AutomobileRepository();
    }

    public AutomobileService(Repository<Automobile> repository) {
        this.repository = repository;
    }

    public List<Automobile> createAutos(int count) {
        List<Automobile> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Automobile auto = new Automobile(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)).setScale(2, RoundingMode.CEILING),
                    "Model-" + RANDOM.nextInt(1000)
            );
            result.add(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    protected AutomobileManufacturers getRandomManufacturer() {
        final AutomobileManufacturers[] values = AutomobileManufacturers.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
