package ua.com.alevel.alexshent.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.model.Bicycle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BicycleRepository implements Repository<Bicycle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BicycleRepository.class);
    private final List<Bicycle> bicycles;

    public BicycleRepository() {
        bicycles = new LinkedList<>();
    }

    @Override
    public Optional<Bicycle> getById(String id) {
        for (Bicycle bicycle : bicycles) {
            if (bicycle.getId().equals(id)) {
                return Optional.of(bicycle);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Optional<Bicycle>> getAll() {
        List<Optional<Bicycle>> result = new LinkedList<>();
        for (Bicycle bicycle : bicycles) {
            if (bicycle != null) {
                result.add(Optional.of(bicycle));
            } else {
                result.add(Optional.empty());
            }
        }
        return result;
    }

    @Override
    public boolean add(Bicycle bicycle) {
        bicycles.add(bicycle);
        return true;
    }

    @Override
    public boolean add(Optional<Bicycle> vehicleOptional) {
        return false;
    }

    @Override
    public boolean addList(List<Bicycle> bicycles) {
        return this.bicycles.addAll(bicycles);
    }

    @Override
    public boolean update(Bicycle bicycle) {
        final Optional<Bicycle> foundBicycle = getById(bicycle.getId());
        if (foundBicycle.isPresent()) {
            Bicycle.copy(bicycle, foundBicycle.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Bicycle> iterator = bicycles.iterator();
        while (iterator.hasNext()) {
            final Bicycle bicycle = iterator.next();
            if (bicycle.getId().equals(id)) {
                LOGGER.debug("deleted bicycle {}", id);
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
