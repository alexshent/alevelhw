package ua.com.alevel.alexshent.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class AutomobileRepository implements Repository<Automobile> {

    static class NotEmptyOptionalSupplier implements Supplier<Optional<Automobile>> {
        @Override
        public Optional<Automobile> get() {
            return Optional.of(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(9.99), "BBB"));
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AutomobileRepository.class);
    private final List<Automobile> automobiles;

    public AutomobileRepository() {
        automobiles = new LinkedList<>();
    }

    @Override
    public Optional<Automobile> getById(String id) {
        for (Automobile auto : automobiles) {
            if (auto.getId().equals(id)) {
                return Optional.of(auto);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Optional<Automobile>> getAll() {
        List<Optional<Automobile>> result = new LinkedList<>();
        for (Automobile automobile : automobiles) {
            if (automobile != null) {
                result.add(Optional.of(automobile));
            } else {
                result.add(Optional.empty());
            }
        }
        return result;
    }

    /**
     * Optional.or()
     *
     * @return list of optionals without empty values
     */
    public List<Optional<Automobile>> getAllNoEmpty() {
        List<Optional<Automobile>> automobilesFromRepository = this.getAll();
        List<Optional<Automobile>> automobiles = new LinkedList<>();
        for (Optional<Automobile> autoOptional : automobilesFromRepository) {
            automobiles.add(autoOptional.or(new NotEmptyOptionalSupplier()));
        }
        return automobiles;
    }

    /**
     * Optional.ifPresentOrElse()
     * Optional.orElse()
     * Optional.orElseGet()
     * Optional.orElseThrow()
     * Optional.filter()
     * Optional.map()
     *
     * @param automobileOptional automobile optional value
     * @return true on success
     */
    @Override
    public boolean add(Optional<Automobile> automobileOptional) {

        automobileOptional.ifPresentOrElse(automobile -> {
            BigDecimal price = automobile.getPrice();
            price.add(BigDecimal.TEN);
        }, () -> {
        });

        Automobile automobileOpel = automobileOptional.orElseGet(() -> {
            Automobile automobile = new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(9.99), "BBB");
            automobile.setManufacturer(AutomobileManufacturers.OPEL);
            return automobile;
        });
        LOGGER.debug("automobileOpel = {}", automobileOpel);

        Automobile discountedAutomobile =
                automobileOptional.map(value -> {
                            value.setBodyType("MMM");
                            return value;
                        })
                        .filter(automobile -> automobile.getPrice().compareTo(BigDecimal.valueOf(9999.99)) > 0)
                        .orElse(new Automobile("AAA", AutomobileManufacturers.BMW, BigDecimal.valueOf(9.99), "BBB"));
        LOGGER.debug("discountedAutomobile = {}", discountedAutomobile);

        return automobiles.add(automobileOptional.orElseThrow());
    }

    @Override
    public boolean add(Automobile auto) {
        automobiles.add(auto);
        return true;
    }

    @Override
    public boolean addList(List<Automobile> autos) {
        return automobiles.addAll(autos);
    }

    /**
     * Optional.ifPresent()
     *
     * @param auto object to update
     * @return true on success
     */
    @Override
    public boolean update(Automobile auto) {
        final Optional<Automobile> foundAuto = getById(auto.getId());
        if (foundAuto.isPresent()) {
            foundAuto.ifPresent(value -> Automobile.copy(auto, value));
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
