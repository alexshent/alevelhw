package ua.com.alevel.alexshent.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.Boat;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BoatRepository implements Repository<Boat> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoatRepository.class);
    private final List<Boat> boats;

    public BoatRepository() {
        boats = new LinkedList<>();
    }

    @Override
    public Boat getById(String id) {
        for (Boat boat : boats) {
            if (boat.getId().equals(id)) {
                return boat;
            }
        }
        return null;
    }

    @Override
    public List<Boat> getAll() {
        return boats;
    }

    @Override
    public boolean create(Boat boat) {
        boats.add(boat);
        return true;
    }

    @Override
    public boolean create(List<Boat> boats) {
        return this.boats.addAll(boats);
    }

    @Override
    public boolean update(Boat boat) {
        final Boat foundBoat = getById(boat.getId());
        if (foundBoat != null) {
            Boat.copy(boat, foundBoat);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Boat> iterator = boats.iterator();
        while (iterator.hasNext()) {
            final Boat boat = iterator.next();
            if (boat.getId().equals(id)) {
                LOGGER.debug("deleted boat {}", id);
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
