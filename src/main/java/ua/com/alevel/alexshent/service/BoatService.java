package ua.com.alevel.alexshent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.annotation.Autowired;
import ua.com.alevel.alexshent.annotation.Singleton;
import ua.com.alevel.alexshent.model.Boat;
import ua.com.alevel.alexshent.model.BoatManufactures;
import ua.com.alevel.alexshent.repository.BoatRepository;
import ua.com.alevel.alexshent.repository.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Singleton
public class BoatService extends Service<Boat> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoatService.class);

    public BoatService() {
        repository = new BoatRepository();
    }

    @Autowired
    public BoatService(BoatRepository repository) {
        this.repository = repository;
    }

    public List<Boat> createBoats(int count) {
        List<Boat> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Boat boat = new Boat(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)).setScale(2, RoundingMode.CEILING),
                    RANDOM.nextBoolean()
            );
            result.add(boat);
            LOGGER.debug("Created boat {}", boat.getId());
        }
        return result;
    }

    protected BoatManufactures getRandomManufacturer() {
        final BoatManufactures[] values = BoatManufactures.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
