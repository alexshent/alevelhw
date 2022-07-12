package ua.com.alevel.alexshent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.model.Bicycle;
import ua.com.alevel.alexshent.model.BicycleManufactures;
import ua.com.alevel.alexshent.repository.BicycleRepository;
import ua.com.alevel.alexshent.repository.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BicycleService extends Service<Bicycle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BicycleService.class);

    public BicycleService() {
        repository = new BicycleRepository();
    }

    public List<Bicycle> createBicycles(int count) {
        List<Bicycle> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Bicycle bicycle = new Bicycle(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)).setScale(2, RoundingMode.CEILING),
                    RANDOM.nextInt(4) + 1
            );
            result.add(bicycle);
            LOGGER.debug("Created bicycle {}", bicycle.getId());
        }
        return result;
    }

    protected BicycleManufactures getRandomManufacturer() {
        final BicycleManufactures[] values = BicycleManufactures.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
