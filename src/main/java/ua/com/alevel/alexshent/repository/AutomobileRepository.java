package ua.com.alevel.alexshent.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.service.AutomobileService;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AutomobileRepository implements Repository<Automobile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutomobileRepository.class);
    private final List<Automobile> automobiles;

    public AutomobileRepository() {
        automobiles = new LinkedList<>();
    }

    @Override
    public Automobile getById(String id) {
        for (Automobile auto : automobiles) {
            if (auto.getId().equals(id)) {
                return auto;
            }
        }
        return null;
    }

    @Override
    public List<Automobile> getAll() {
        return automobiles;
    }

    @Override
    public boolean create(Automobile auto) {
        automobiles.add(auto);
        return true;
    }

    @Override
    public boolean create(List<Automobile> autos) {
        return automobiles.addAll(autos);
    }

    @Override
    public boolean update(Automobile auto) {
        final Automobile foundAuto = getById(auto.getId());
        if (foundAuto != null) {
            Automobile.copy(auto, foundAuto);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Automobile> iterator = automobiles.iterator();
        while (iterator.hasNext()) {
            final Automobile auto = iterator.next();
            if (auto.getId().equals(id)) {
                LOGGER.debug("deleted auto {}", id);
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
