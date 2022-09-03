package ua.com.alevel.alexshent.repository.database;

import ua.com.alevel.alexshent.JDBC;
import ua.com.alevel.alexshent.exceptions.SQLQueryException;
import ua.com.alevel.alexshent.model.Automobile;
import ua.com.alevel.alexshent.model.AutomobileBuilder;
import ua.com.alevel.alexshent.model.AutomobileManufacturers;
import ua.com.alevel.alexshent.model.Engine;
import ua.com.alevel.alexshent.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AutomobileRepository implements Repository<Automobile> {
    private final Connection connection = JDBC.getConnection();

    @Override
    public Optional<Automobile> getById(String id) {

        final String sql = """
                SELECT automobiles.*, automobile_manufacturers.name AS %s, engines.volume AS %s, engines.brand AS %s
                FROM public.automobiles
                JOIN public.automobile_manufacturers ON automobile_manufacturers.manufacturer_id = automobiles.manufacturer_id
                JOIN public.engines ON engines.engine_id = automobiles.engine_id
                WHERE automobile_id = uuid(?)
                """
                .formatted(
                        AutomobileTable.LABEL_AUTOMOBILE_MANUFACTURER_NAME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_VOLUME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_BRAND
                );

        enum Automobiles {
            ZERO,
            INDEX_AUTOMOBILE_ID
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(Automobiles.INDEX_AUTOMOBILE_ID.ordinal(), id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    Automobile automobile = getAutomobileFromResultSet(resultSet);
                    return Optional.of(automobile);
                }
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Optional<Automobile>> getAll() {
        List<Optional<Automobile>> list = new ArrayList<>();
        final String sql = """
                SELECT automobiles.*, automobile_manufacturers.name AS %s, engines.volume AS %s, engines.brand AS %s
                FROM public.automobiles
                JOIN public.automobile_manufacturers ON automobile_manufacturers.manufacturer_id = automobiles.manufacturer_id
                JOIN public.engines ON engines.engine_id = automobiles.engine_id
                """
                .formatted(
                        AutomobileTable.LABEL_AUTOMOBILE_MANUFACTURER_NAME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_VOLUME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_BRAND
                );

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Automobile automobile = getAutomobileFromResultSet(resultSet);
                    list.add(Optional.of(automobile));
                }
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return list;
    }

    @Override
    public boolean add(Automobile automobile) {
        final String sql = """
                INSERT INTO public.automobiles
                (automobile_id, model, price, components, manufacturer_id, body_type, created_at, trip_counter, engine_id, invoice_id)
                VALUES (
                uuid(?), ?, ?, ?,
                (SELECT manufacturer_id FROM public.automobile_manufacturers WHERE name = ?),
                ?, ?, ?,
                (SELECT engine_id FROM public.engines WHERE volume = ? AND brand = ?),
                uuid(?)
                )
                """;

        enum CreateAutomobile {
            ZERO,
            INDEX_AUTOMOBILE_ID,
            INDEX_AUTOMOBILE_MODEL,
            INDEX_AUTOMOBILE_PRICE,
            INDEX_AUTOMOBILE_COMPONENTS,
            INDEX_AUTOMOBILE_MANUFACTURER_ID,
            INDEX_AUTOMOBILE_BODY_TYPE,
            INDEX_AUTOMOBILE_CREATED_AT,
            INDEX_AUTOMOBILE_TRIP_COUNTER,
            INDEX_AUTOMOBILE_ENGINE_VOLUME,
            INDEX_AUTOMOBILE_ENGINE_BRAND,
            INDEX_AUTOMOBILE_INVOICE_ID
        }

        final String componentsArrayType = "varchar";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // automobile_id
            ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_ID.ordinal(), automobile.getId());

            // model
            ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_MODEL.ordinal(), automobile.getModel());

            // price
            ps.setFloat(CreateAutomobile.INDEX_AUTOMOBILE_PRICE.ordinal(), automobile.getPrice().floatValue());

            // components
            List<String> components = automobile.getComponents().isPresent() ? automobile.getComponents().get() : new ArrayList<>();
            String[] componentsArray = new String[components.size()];
            components.toArray(componentsArray);
            // https://jdbc.postgresql.org/documentation/head/arrays.html
            Array postgresArray = connection.createArrayOf(componentsArrayType, componentsArray);
            ps.setArray(CreateAutomobile.INDEX_AUTOMOBILE_COMPONENTS.ordinal(), postgresArray);

            // manufacturer_id
            ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_MANUFACTURER_ID.ordinal(), automobile.getManufacturer().name());

            // body_type
            ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_BODY_TYPE.ordinal(), automobile.getBodyType());

            // created_at
            ps.setTimestamp(CreateAutomobile.INDEX_AUTOMOBILE_CREATED_AT.ordinal(), Timestamp.valueOf(automobile.getCreatedAt()));

            // trip_counter
            ps.setLong(CreateAutomobile.INDEX_AUTOMOBILE_TRIP_COUNTER.ordinal(), automobile.getTripCounter());

            // engine_id
            ps.setInt(CreateAutomobile.INDEX_AUTOMOBILE_ENGINE_VOLUME.ordinal(), automobile.getEngine().getVolume());
            ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_ENGINE_BRAND.ordinal(), automobile.getEngine().getBrand());

            // invoice_id
            ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_INVOICE_ID.ordinal(), automobile.getInvoiceId());

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean add(Optional<Automobile> itemOptional) {
        if (itemOptional.isPresent()) {
            return this.add(itemOptional.get());
        }
        return false;
    }

    @Override
    public boolean addList(List<Automobile> automobiles) {
        final String sql = """
                INSERT INTO public.automobiles
                (automobile_id, model, price, components, manufacturer_id, body_type, created_at, trip_counter, engine_id, invoice_id)
                VALUES (
                uuid(?), ?, ?, ?,
                (SELECT manufacturer_id FROM public.automobile_manufacturers WHERE name = ?),
                ?, ?, ?,
                (SELECT engine_id FROM public.engines WHERE volume = ? AND brand = ?),
                uuid(?)
                )
                """;

        enum CreateAutomobile {
            ZERO,
            INDEX_AUTOMOBILE_ID,
            INDEX_AUTOMOBILE_MODEL,
            INDEX_AUTOMOBILE_PRICE,
            INDEX_AUTOMOBILE_COMPONENTS,
            INDEX_AUTOMOBILE_MANUFACTURER_ID,
            INDEX_AUTOMOBILE_BODY_TYPE,
            INDEX_AUTOMOBILE_CREATED_AT,
            INDEX_AUTOMOBILE_TRIP_COUNTER,
            INDEX_AUTOMOBILE_ENGINE_VOLUME,
            INDEX_AUTOMOBILE_ENGINE_BRAND,
            INDEX_AUTOMOBILE_INVOICE_ID
        }

        final String componentsArrayType = "varchar";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Automobile automobile : automobiles) {
                // automobile_id
                ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_ID.ordinal(), automobile.getId());

                // model
                ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_MODEL.ordinal(), automobile.getModel());

                // price
                ps.setFloat(CreateAutomobile.INDEX_AUTOMOBILE_PRICE.ordinal(), automobile.getPrice().floatValue());

                // components
                List<String> components = automobile.getComponents().isPresent() ? automobile.getComponents().get() : new ArrayList<>();
                String[] componentsArray = new String[components.size()];
                components.toArray(componentsArray);
                // https://jdbc.postgresql.org/documentation/head/arrays.html
                Array postgresArray = connection.createArrayOf(componentsArrayType, componentsArray);
                ps.setArray(CreateAutomobile.INDEX_AUTOMOBILE_COMPONENTS.ordinal(), postgresArray);

                // manufacturer_id
                ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_MANUFACTURER_ID.ordinal(), automobile.getManufacturer().name());

                // body_type
                ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_BODY_TYPE.ordinal(), automobile.getBodyType());

                // created_at
                ps.setTimestamp(CreateAutomobile.INDEX_AUTOMOBILE_CREATED_AT.ordinal(), Timestamp.valueOf(automobile.getCreatedAt()));

                // trip_counter
                ps.setLong(CreateAutomobile.INDEX_AUTOMOBILE_TRIP_COUNTER.ordinal(), automobile.getTripCounter());

                // engine_id
                ps.setInt(CreateAutomobile.INDEX_AUTOMOBILE_ENGINE_VOLUME.ordinal(), automobile.getEngine().getVolume());
                ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_ENGINE_BRAND.ordinal(), automobile.getEngine().getBrand());

                // invoice_id
                ps.setString(CreateAutomobile.INDEX_AUTOMOBILE_INVOICE_ID.ordinal(), automobile.getInvoiceId());

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Automobile automobile) {
        final String sql = """
                UPDATE public.automobiles
                SET (model, price, components, manufacturer_id, body_type, created_at, trip_counter, engine_id, invoice_id)
                = (
                ?, ?, ?,
                (SELECT manufacturer_id FROM public.automobile_manufacturers WHERE name = ?),
                ?, ?, ?,
                (SELECT engine_id FROM public.engines WHERE volume = ? AND brand = ?),
                uuid(?)
                )
                WHERE automobile_id = uuid(?)
                """;

        enum UpdateAutomobile {
            ZERO,
            INDEX_AUTOMOBILE_MODEL,
            INDEX_AUTOMOBILE_PRICE,
            INDEX_AUTOMOBILE_COMPONENTS,
            INDEX_AUTOMOBILE_MANUFACTURER_ID,
            INDEX_AUTOMOBILE_BODY_TYPE,
            INDEX_AUTOMOBILE_CREATED_AT,
            INDEX_AUTOMOBILE_TRIP_COUNTER,
            INDEX_AUTOMOBILE_ENGINE_VOLUME,
            INDEX_AUTOMOBILE_ENGINE_BRAND,
            INDEX_AUTOMOBILE_INVOICE_ID,
            INDEX_AUTOMOBILE_ID
        }

        final String componentsArrayType = "varchar";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // model
            ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_MODEL.ordinal(), automobile.getModel());

            // price
            ps.setFloat(UpdateAutomobile.INDEX_AUTOMOBILE_PRICE.ordinal(), automobile.getPrice().floatValue());

            // components
            List<String> components = automobile.getComponents().isPresent() ? automobile.getComponents().get() : new ArrayList<>();
            String[] componentsArray = new String[components.size()];
            components.toArray(componentsArray);
            // https://jdbc.postgresql.org/documentation/head/arrays.html
            Array postgresArray = connection.createArrayOf(componentsArrayType, componentsArray);
            ps.setArray(UpdateAutomobile.INDEX_AUTOMOBILE_COMPONENTS.ordinal(), postgresArray);

            // manufacturer_id
            ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_MANUFACTURER_ID.ordinal(), automobile.getManufacturer().name());

            // body_type
            ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_BODY_TYPE.ordinal(), automobile.getBodyType());

            // created_at
            ps.setTimestamp(UpdateAutomobile.INDEX_AUTOMOBILE_CREATED_AT.ordinal(), Timestamp.valueOf(automobile.getCreatedAt()));

            // trip_counter
            ps.setLong(UpdateAutomobile.INDEX_AUTOMOBILE_TRIP_COUNTER.ordinal(), automobile.getTripCounter());

            // engine_id
            ps.setInt(UpdateAutomobile.INDEX_AUTOMOBILE_ENGINE_VOLUME.ordinal(), automobile.getEngine().getVolume());
            ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_ENGINE_BRAND.ordinal(), automobile.getEngine().getBrand());

            // invoice_id
            ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_INVOICE_ID.ordinal(), automobile.getInvoiceId());

            // automobile_id
            ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_ID.ordinal(), automobile.getId());

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    public boolean updateList(List<Automobile> automobiles) {

        final String sql = """
                UPDATE public.automobiles
                SET (model, price, components, manufacturer_id, body_type, created_at, trip_counter, engine_id, invoice_id)
                = (
                ?, ?, ?,
                (SELECT manufacturer_id FROM public.automobile_manufacturers WHERE name = ?),
                ?, ?, ?,
                (SELECT engine_id FROM public.engines WHERE volume = ? AND brand = ?),
                uuid(?)
                )
                WHERE automobile_id = uuid(?)
                """;

        enum UpdateAutomobile {
            ZERO,
            INDEX_AUTOMOBILE_MODEL,
            INDEX_AUTOMOBILE_PRICE,
            INDEX_AUTOMOBILE_COMPONENTS,
            INDEX_AUTOMOBILE_MANUFACTURER_ID,
            INDEX_AUTOMOBILE_BODY_TYPE,
            INDEX_AUTOMOBILE_CREATED_AT,
            INDEX_AUTOMOBILE_TRIP_COUNTER,
            INDEX_AUTOMOBILE_ENGINE_VOLUME,
            INDEX_AUTOMOBILE_ENGINE_BRAND,
            INDEX_AUTOMOBILE_INVOICE_ID,
            INDEX_AUTOMOBILE_ID
        }

        final String componentsArrayType = "varchar";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Automobile automobile : automobiles) {
                // model
                ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_MODEL.ordinal(), automobile.getModel());

                // price
                ps.setFloat(UpdateAutomobile.INDEX_AUTOMOBILE_PRICE.ordinal(), automobile.getPrice().floatValue());

                // components
                List<String> components = automobile.getComponents().isPresent() ? automobile.getComponents().get() : new ArrayList<>();
                String[] componentsArray = new String[components.size()];
                components.toArray(componentsArray);
                // https://jdbc.postgresql.org/documentation/head/arrays.html
                Array postgresArray = connection.createArrayOf(componentsArrayType, componentsArray);
                ps.setArray(UpdateAutomobile.INDEX_AUTOMOBILE_COMPONENTS.ordinal(), postgresArray);

                // manufacturer_id
                ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_MANUFACTURER_ID.ordinal(), automobile.getManufacturer().name());

                // body_type
                ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_BODY_TYPE.ordinal(), automobile.getBodyType());

                // created_at
                ps.setTimestamp(UpdateAutomobile.INDEX_AUTOMOBILE_CREATED_AT.ordinal(), Timestamp.valueOf(automobile.getCreatedAt()));

                // trip_counter
                ps.setLong(UpdateAutomobile.INDEX_AUTOMOBILE_TRIP_COUNTER.ordinal(), automobile.getTripCounter());

                // engine_id
                ps.setInt(UpdateAutomobile.INDEX_AUTOMOBILE_ENGINE_VOLUME.ordinal(), automobile.getEngine().getVolume());
                ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_ENGINE_BRAND.ordinal(), automobile.getEngine().getBrand());

                // invoice_id
                ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_INVOICE_ID.ordinal(), automobile.getInvoiceId());

                // automobile_id
                ps.setString(UpdateAutomobile.INDEX_AUTOMOBILE_ID.ordinal(), automobile.getId());

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        final String sql = """
                DELETE FROM public.automobiles
                WHERE automobiles.automobile_id = uuid(?)
                """;

        enum DeleteAutomobile {
            ZERO,
            INDEX_AUTOMOBILE_ID
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(DeleteAutomobile.INDEX_AUTOMOBILE_ID.ordinal(), id);
            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    public Automobile getAutomobileFromResultSet(ResultSet resultSet) {
        try {
            AutomobileBuilder builder = new AutomobileBuilder();

            builder.withId(resultSet.getString(AutomobileTable.LABEL_AUTOMOBILE_ID));

            builder.withModel(resultSet.getString(AutomobileTable.LABEL_AUTOMOBILE_MODEL));

            builder.withPrice(resultSet.getBigDecimal(AutomobileTable.LABEL_AUTOMOBILE_PRICE));

            builder.withBodyType(resultSet.getString(AutomobileTable.LABEL_AUTOMOBILE_BODY_TYPE));

            Array automobileComponents = resultSet.getArray(AutomobileTable.LABEL_AUTOMOBILE_COMPONENTS);
            List<String> components = Arrays.asList((String[])automobileComponents.getArray());
            builder.withComponents(components);

            builder.withInvoiceId(resultSet.getString(InvoiceTable.LABEL_INVOICE_ID));

            AutomobileManufacturers manufacturer = AutomobileManufacturers.valueOf(
                    resultSet.getString(AutomobileTable.LABEL_AUTOMOBILE_MANUFACTURER_NAME)
            );
            builder.withManufacturer(manufacturer);

            LocalDateTime createdAt = Util.timestampStringToLocalDateTime(resultSet.getString(AutomobileTable.LABEL_AUTOMOBILE_CREATED_AT));
            builder.withCreatedAt(createdAt);

            builder.withTripCounter(resultSet.getLong(AutomobileTable.LABEL_AUTOMOBILE_TRIP_COUNTER));

            int engineVolume = resultSet.getInt(AutomobileTable.LABEL_AUTOMOBILE_ENGINE_VOLUME);
            String engineBrand = resultSet.getString(AutomobileTable.LABEL_AUTOMOBILE_ENGINE_BRAND);
            builder.withEngine(new Engine(engineVolume, engineBrand));

            return new Automobile(builder);
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
    }
}
