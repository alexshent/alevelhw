package ua.com.alevel.alexshent.repository.database;

import ua.com.alevel.alexshent.JDBC;
import ua.com.alevel.alexshent.exceptions.SQLQueryException;
import ua.com.alevel.alexshent.model.Bicycle;
import ua.com.alevel.alexshent.model.BicycleBuilder;
import ua.com.alevel.alexshent.model.BicycleManufactures;
import ua.com.alevel.alexshent.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BicycleRepository implements Repository<Bicycle> {
    private final Connection connection = JDBC.getConnection();

    @Override
    public Optional<Bicycle> getById(String id) {
        final String sql = """
                SELECT bicycles.*, bicycle_manufacturers.name AS %s
                FROM public.bicycles
                JOIN public.bicycle_manufacturers ON bicycle_manufacturers.manufacturer_id = bicycles.manufacturer_id
                WHERE bicycle_id = uuid(?)
                """
                .formatted(
                        BicycleTable.LABEL_BICYCLE_MANUFACTURER_NAME
                );

        enum Bicycles {
            ZERO,
            INDEX_BICYCLE_ID
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(Bicycles.INDEX_BICYCLE_ID.ordinal(), id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    Bicycle bicycle = getBicycleFromResultSet(resultSet);
                    return Optional.of(bicycle);
                }
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Optional<Bicycle>> getAll() {
        List<Optional<Bicycle>> list = new ArrayList<>();
        final String sql = """
                SELECT bicycles.*, bicycle_manufacturers.name AS %s
                FROM public.bicycles
                JOIN public.bicycle_manufacturers ON bicycle_manufacturers.manufacturer_id = bicycles.manufacturer_id
                """
                .formatted(
                        BicycleTable.LABEL_BICYCLE_MANUFACTURER_NAME
                );

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Bicycle bicycle = getBicycleFromResultSet(resultSet);
                    list.add(Optional.of(bicycle));
                }
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return list;
    }

    @Override
    public boolean add(Bicycle bicycle) {
        final String sql = """
                INSERT INTO public.bicycles
                (bicycle_id, model, price, components, manufacturer_id, number_of_wheels, invoice_id)
                VALUES (
                uuid(?), ?, ?, ?,
                (SELECT manufacturer_id FROM public.bicycle_manufacturers WHERE name = ?),
                ?,
                uuid(?)
                )
                """;

        enum CreateBicycle {
            ZERO,
            INDEX_BICYCLE_ID,
            INDEX_BICYCLE_MODEL,
            INDEX_BICYCLE_PRICE,
            INDEX_BICYCLE_COMPONENTS,
            INDEX_BICYCLE_MANUFACTURER_ID,
            INDEX_BICYCLE_NUMBER_OF_WHEELS,
            INDEX_BICYCLE_INVOICE_ID
        }

        final String componentsArrayType = "varchar";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // bicycle_id
            ps.setString(CreateBicycle.INDEX_BICYCLE_ID.ordinal(), bicycle.getId());

            // model
            ps.setString(CreateBicycle.INDEX_BICYCLE_MODEL.ordinal(), bicycle.getModel());

            // price
            ps.setFloat(CreateBicycle.INDEX_BICYCLE_PRICE.ordinal(), bicycle.getPrice().floatValue());

            // components
            List<String> components = bicycle.getComponents().isPresent() ? bicycle.getComponents().get() : new ArrayList<>();
            String[] componentsArray = new String[components.size()];
            components.toArray(componentsArray);
            // https://jdbc.postgresql.org/documentation/head/arrays.html
            Array postgresArray = connection.createArrayOf(componentsArrayType, componentsArray);
            ps.setArray(CreateBicycle.INDEX_BICYCLE_COMPONENTS.ordinal(), postgresArray);

            // manufacturer_id
            ps.setString(CreateBicycle.INDEX_BICYCLE_MANUFACTURER_ID.ordinal(), bicycle.getManufacturer().name());

            // number_of_wheels
            ps.setInt(CreateBicycle.INDEX_BICYCLE_NUMBER_OF_WHEELS.ordinal(), bicycle.getNumberOfWheels());

            // invoice_id
            ps.setString(CreateBicycle.INDEX_BICYCLE_INVOICE_ID.ordinal(), bicycle.getInvoiceId());

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean add(Optional<Bicycle> itemOptional) {
        if (itemOptional.isPresent()) {
            return this.add(itemOptional.get());
        }
        return false;
    }

    @Override
    public boolean addList(List<Bicycle> bicycles) {
        final String sql = """
                INSERT INTO public.bicycles
                (bicycle_id, model, price, components, manufacturer_id, number_of_wheels, invoice_id)
                VALUES (
                uuid(?), ?, ?, ?,
                (SELECT manufacturer_id FROM public.bicycle_manufacturers WHERE name = ?),
                ?,
                uuid(?)
                )
                """;

        enum CreateBicycle {
            ZERO,
            INDEX_BICYCLE_ID,
            INDEX_BICYCLE_MODEL,
            INDEX_BICYCLE_PRICE,
            INDEX_BICYCLE_COMPONENTS,
            INDEX_BICYCLE_MANUFACTURER_ID,
            INDEX_BICYCLE_NUMBER_OF_WHEELS,
            INDEX_BICYCLE_INVOICE_ID
        }

        final String componentsArrayType = "varchar";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Bicycle bicycle : bicycles) {
                // bicycle_id
                ps.setString(CreateBicycle.INDEX_BICYCLE_ID.ordinal(), bicycle.getId());

                // model
                ps.setString(CreateBicycle.INDEX_BICYCLE_MODEL.ordinal(), bicycle.getModel());

                // price
                ps.setFloat(CreateBicycle.INDEX_BICYCLE_PRICE.ordinal(), bicycle.getPrice().floatValue());

                // components
                List<String> components = bicycle.getComponents().isPresent() ? bicycle.getComponents().get() : new ArrayList<>();
                String[] componentsArray = new String[components.size()];
                components.toArray(componentsArray);
                // https://jdbc.postgresql.org/documentation/head/arrays.html
                Array postgresArray = connection.createArrayOf(componentsArrayType, componentsArray);
                ps.setArray(CreateBicycle.INDEX_BICYCLE_COMPONENTS.ordinal(), postgresArray);

                // manufacturer_id
                ps.setString(CreateBicycle.INDEX_BICYCLE_MANUFACTURER_ID.ordinal(), bicycle.getManufacturer().name());

                // number_of_wheels
                ps.setInt(CreateBicycle.INDEX_BICYCLE_NUMBER_OF_WHEELS.ordinal(), bicycle.getNumberOfWheels());

                // invoice_id
                ps.setString(CreateBicycle.INDEX_BICYCLE_INVOICE_ID.ordinal(), bicycle.getInvoiceId());

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Bicycle bicycle) {
        final String sql = """
                UPDATE public.bicycles
                SET (model, price, components, manufacturer_id, number_of_wheels, invoice_id)
                = (
                ?, ?, ?,
                (SELECT manufacturer_id FROM public.bicycle_manufacturers WHERE name = ?),
                ?,
                uuid(?)
                )
                WHERE bicycle_id = uuid(?)
                """;

        enum UpdateBicycle {
            ZERO,
            INDEX_BICYCLE_MODEL,
            INDEX_BICYCLE_PRICE,
            INDEX_BICYCLE_COMPONENTS,
            INDEX_BICYCLE_MANUFACTURER_ID,
            INDEX_BICYCLE_NUMBER_OF_WHEELS,
            INDEX_BICYCLE_INVOICE_ID,
            INDEX_BICYCLE_ID,
        }

        final String componentsArrayType = "varchar";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // model
            ps.setString(UpdateBicycle.INDEX_BICYCLE_MODEL.ordinal(), bicycle.getModel());

            // price
            ps.setFloat(UpdateBicycle.INDEX_BICYCLE_PRICE.ordinal(), bicycle.getPrice().floatValue());

            // components
            List<String> components = bicycle.getComponents().isPresent() ? bicycle.getComponents().get() : new ArrayList<>();
            String[] componentsArray = new String[components.size()];
            components.toArray(componentsArray);
            // https://jdbc.postgresql.org/documentation/head/arrays.html
            Array postgresArray = connection.createArrayOf(componentsArrayType, componentsArray);
            ps.setArray(UpdateBicycle.INDEX_BICYCLE_COMPONENTS.ordinal(), postgresArray);

            // manufacturer_id
            ps.setString(UpdateBicycle.INDEX_BICYCLE_MANUFACTURER_ID.ordinal(), bicycle.getManufacturer().name());

            // number_of_wheels
            ps.setInt(UpdateBicycle.INDEX_BICYCLE_NUMBER_OF_WHEELS.ordinal(), bicycle.getNumberOfWheels());

            // invoice_id
            ps.setString(UpdateBicycle.INDEX_BICYCLE_INVOICE_ID.ordinal(), bicycle.getInvoiceId());

            // bicycle_id
            ps.setString(UpdateBicycle.INDEX_BICYCLE_ID.ordinal(), bicycle.getId());

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        final String sql = """
                DELETE FROM public.bicycles
                WHERE bicycles.bicycle_id = uuid(?)
                """;

        enum DeleteBicycle {
            ZERO,
            INDEX_BICYCLE_ID
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(DeleteBicycle.INDEX_BICYCLE_ID.ordinal(), id);
            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    public Bicycle getBicycleFromResultSet(ResultSet resultSet) {
        try {
            BicycleBuilder builder = new BicycleBuilder();

            builder.withId(resultSet.getString(BicycleTable.LABEL_BICYCLE_ID));

            builder.withModel(resultSet.getString(BicycleTable.LABEL_BICYCLE_MODEL));

            builder.withPrice(resultSet.getBigDecimal(BicycleTable.LABEL_BICYCLE_PRICE));

            Array bicycleComponents = resultSet.getArray(BicycleTable.LABEL_BICYCLE_COMPONENTS);
            List<String> components = Arrays.asList((String[])bicycleComponents.getArray());
            builder.withComponents(components);

            BicycleManufactures manufacturer = BicycleManufactures.valueOf(
                    resultSet.getString(BicycleTable.LABEL_BICYCLE_MANUFACTURER_NAME)
            );
            builder.withManufacturer(manufacturer);

            builder.withNumberOfWheels(resultSet.getInt(BicycleTable.LABEL_BICYCLE_NUMBER_OF_WHEELS));

            builder.withInvoiceId(resultSet.getString(InvoiceTable.LABEL_INVOICE_ID));

            return new Bicycle(builder);
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
    }
}
