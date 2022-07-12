package ua.com.alevel.alexshent.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.model.Bicycle;
import ua.com.alevel.alexshent.service.BicycleService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BicycleRepository implements Repository<Bicycle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BicycleRepository.class);
    private final List<Bicycle> bicycles;

    public BicycleRepository() {
        bicycles = new LinkedList<>();
    }

    @Override
    public Bicycle getById(String id) {
        for (Bicycle bicycle : bicycles) {
            if (bicycle.getId().equals(id)) {
                return bicycle;
            }
        }
        return null;
    }

    @Override
    public List<Bicycle> getAll() {
        return bicycles;
    }

    @Override
    public boolean create(Bicycle bicycle) {
        bicycles.add(bicycle);
        return true;
    }

    @Override
    public boolean create(List<Bicycle> bicycles) {
        return this.bicycles.addAll(bicycles);
    }

    @Override
    public boolean update(Bicycle bicycle) {
        final Bicycle foundBicycle = getById(bicycle.getId());
        if (foundBicycle != null) {
            Bicycle.copy(bicycle, foundBicycle);
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
