package ua.com.alevel.alexshent.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.annotation.Singleton;
import ua.com.alevel.alexshent.model.Boat;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Singleton
public class BoatRepository implements Repository<Boat> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoatRepository.class);
    private final List<Boat> boats;

    public BoatRepository() {
        boats = new LinkedList<>();
    }

    @Override
    public Optional<Boat> getById(String id) {
        for (Boat boat : boats) {
            if (boat.getId().equals(id)) {
                return Optional.of(boat);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Optional<Boat>> getAll() {
        List<Optional<Boat>> result = new LinkedList<>();
        for (Boat boat : boats) {
            if (boat != null) {
                result.add(Optional.of(boat));
            } else {
                result.add(Optional.empty());
            }
        }
        return result;
    }

    @Override
    public boolean add(Boat boat) {
        boats.add(boat);
        return true;
    }

    @Override
    public boolean add(Optional<Boat> vehicleOptional) {
        return false;
    }

    @Override
    public boolean addList(List<Boat> boats) {
        return this.boats.addAll(boats);
    }

    @Override
    public boolean update(Boat boat) {
        final Optional<Boat> foundBoat = getById(boat.getId());
        if (foundBoat.isPresent()) {
            Boat.copy(boat, foundBoat.get());
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
