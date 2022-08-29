package ua.com.alevel.alexshent.repository.database;

import ua.com.alevel.alexshent.JDBC;
import ua.com.alevel.alexshent.exceptions.SQLQueryException;
import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.repository.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class InvoiceRepository implements Repository<Invoice> {
    private final Connection connection = JDBC.getConnection();

    private List<Optional<Invoice>> getInvoicesFromResultSet(List<ResultSet> resultSets) {
        Map<String, Invoice> invoicesMap = new HashMap<>();
        AutomobileRepository automobileRepository = new AutomobileRepository();
        BicycleRepository bicycleRepository = new BicycleRepository();
        List<Optional<Invoice>> invoices = new ArrayList<>();
        InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
        resultSets.forEach(resultSet -> {
            try {
                while (resultSet.next()) {
                    invoiceBuilder.withId(resultSet.getString(InvoiceTable.LABEL_INVOICE_ID));
                    invoiceBuilder.withCreatedAt(Util.timestampStringToLocalDateTime(resultSet.getString(InvoiceTable.LABEL_INVOICE_CREATED_AT)));
                    invoiceBuilder.withName(resultSet.getString(InvoiceTable.LABEL_INVOICE_NAME));
                    Invoice invoice = invoiceBuilder.build();

                    List<Vehicle> invoiceVehicles;

                    if (invoicesMap.containsKey(invoice.getId())) {
                        invoiceVehicles = invoicesMap.get(invoice.getId()).getVehicles();
                    } else {
                        invoiceVehicles = new ArrayList<>();
                        invoice.setVehicles(invoiceVehicles);
                        invoicesMap.put(invoice.getId(), invoice);
                    }

                    if (Util.hasColumn(resultSet, AutomobileTable.LABEL_AUTOMOBILE_ID)) {
                        Automobile automobile = automobileRepository.getAutomobileFromResultSet(resultSet);
                        invoiceVehicles.add(automobile);
                    } else if (Util.hasColumn(resultSet, BicycleTable.LABEL_BICYCLE_ID)) {
                        Bicycle bicycle = bicycleRepository.getBicycleFromResultSet(resultSet);
                        invoiceVehicles.add(bicycle);
                    }
                }
            } catch (SQLException e) {
                throw new SQLQueryException(e.getMessage());
            }
        });
        for (Map.Entry <String, Invoice> entry : invoicesMap.entrySet()) {
            invoices.add(Optional.of(entry.getValue()));
        }
        return invoices;
    }

    @Override
    public Optional<Invoice> getById(String id) {

        final String sqlInvoicesAutomobiles = """
                SELECT
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                automobiles.automobile_id AS %s,
                automobiles.model AS %s,
                automobiles.price AS %s,
                automobiles.components AS %s,
                automobile_manufacturers.name AS %s,
                automobiles.body_type AS %s,
                automobiles.created_at AS %s,
                automobiles.trip_counter AS %s,
                engines.volume AS %s,
                engines.brand AS %s
                FROM public.invoices
                JOIN public.automobiles ON invoices.invoice_id = automobiles.invoice_id
                JOIN public.automobile_manufacturers ON automobile_manufacturers.manufacturer_id = automobiles.manufacturer_id
                JOIN public.engines ON engines.engine_id = automobiles.engine_id
                WHERE invoices.invoice_id = uuid(?)
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        AutomobileTable.LABEL_AUTOMOBILE_ID,
                        AutomobileTable.LABEL_AUTOMOBILE_MODEL,
                        AutomobileTable.LABEL_AUTOMOBILE_PRICE,
                        AutomobileTable.LABEL_AUTOMOBILE_COMPONENTS,
                        AutomobileTable.LABEL_AUTOMOBILE_MANUFACTURER_NAME,
                        AutomobileTable.LABEL_AUTOMOBILE_BODY_TYPE,
                        AutomobileTable.LABEL_AUTOMOBILE_CREATED_AT,
                        AutomobileTable.LABEL_AUTOMOBILE_TRIP_COUNTER,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_VOLUME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_BRAND
                );

        final String sqlInvoicesBicycles = """
                SELECT
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                bicycles.bicycle_id AS %s,
                bicycles.model AS %s,
                bicycles.price AS %s,
                bicycles.components AS %s,
                bicycle_manufacturers.name AS %s,
                bicycles.number_of_wheels AS %s
                FROM public.invoices
                JOIN public.bicycles ON invoices.invoice_id = bicycles.invoice_id
                JOIN public.bicycle_manufacturers ON bicycle_manufacturers.manufacturer_id = bicycles.manufacturer_id
                WHERE invoices.invoice_id = uuid(?)
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        BicycleTable.LABEL_BICYCLE_ID,
                        BicycleTable.LABEL_BICYCLE_MODEL,
                        BicycleTable.LABEL_BICYCLE_PRICE,
                        BicycleTable.LABEL_BICYCLE_COMPONENTS,
                        BicycleTable.LABEL_BICYCLE_MANUFACTURER_NAME,
                        BicycleTable.LABEL_BICYCLE_NUMBER_OF_WHEELS
                );

        try (
                PreparedStatement ps1 = connection.prepareStatement(sqlInvoicesAutomobiles);
                PreparedStatement ps2 = connection.prepareStatement(sqlInvoicesBicycles)
        ) {
            ps1.setString(1, id);
            ps2.setString(1, id);
            try (
                    ResultSet resultSet1 = ps1.executeQuery();
                    ResultSet resultSet2 = ps2.executeQuery()
            ) {
                List<ResultSet> resultSets = new ArrayList<>();
                resultSets.add(resultSet1);
                resultSets.add(resultSet2);
                List<Optional<Invoice>> invoices = getInvoicesFromResultSet(resultSets);
                if (invoices.size() == 1) {
                    return invoices.get(0);
                }
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Optional<Invoice>> getAll() {

        final String sqlInvoicesAutomobiles = """
                SELECT
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                automobiles.automobile_id AS %s,
                automobiles.model AS %s,
                automobiles.price AS %s,
                automobiles.components AS %s,
                automobile_manufacturers.name AS %s,
                automobiles.body_type AS %s,
                automobiles.created_at AS %s,
                automobiles.trip_counter AS %s,
                engines.volume AS %s,
                engines.brand AS %s
                FROM public.invoices
                JOIN public.automobiles ON invoices.invoice_id = automobiles.invoice_id
                JOIN public.automobile_manufacturers ON automobile_manufacturers.manufacturer_id = automobiles.manufacturer_id
                JOIN public.engines ON engines.engine_id = automobiles.engine_id
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        AutomobileTable.LABEL_AUTOMOBILE_ID,
                        AutomobileTable.LABEL_AUTOMOBILE_MODEL,
                        AutomobileTable.LABEL_AUTOMOBILE_PRICE,
                        AutomobileTable.LABEL_AUTOMOBILE_COMPONENTS,
                        AutomobileTable.LABEL_AUTOMOBILE_MANUFACTURER_NAME,
                        AutomobileTable.LABEL_AUTOMOBILE_BODY_TYPE,
                        AutomobileTable.LABEL_AUTOMOBILE_CREATED_AT,
                        AutomobileTable.LABEL_AUTOMOBILE_TRIP_COUNTER,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_VOLUME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_BRAND
                );

        final String sqlInvoicesBicycles = """
                SELECT
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                bicycles.bicycle_id AS %s,
                bicycles.model AS %s,
                bicycles.price AS %s,
                bicycles.components AS %s,
                bicycle_manufacturers.name AS %s,
                bicycles.number_of_wheels AS %s
                FROM public.invoices
                JOIN public.bicycles ON invoices.invoice_id = bicycles.invoice_id
                JOIN public.bicycle_manufacturers ON bicycle_manufacturers.manufacturer_id = bicycles.manufacturer_id
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        BicycleTable.LABEL_BICYCLE_ID,
                        BicycleTable.LABEL_BICYCLE_MODEL,
                        BicycleTable.LABEL_BICYCLE_PRICE,
                        BicycleTable.LABEL_BICYCLE_COMPONENTS,
                        BicycleTable.LABEL_BICYCLE_MANUFACTURER_NAME,
                        BicycleTable.LABEL_BICYCLE_NUMBER_OF_WHEELS
                );

        try (
                PreparedStatement ps1 = connection.prepareStatement(sqlInvoicesAutomobiles);
                PreparedStatement ps2 = connection.prepareStatement(sqlInvoicesBicycles)
        ) {
            try (
                    ResultSet resultSet1 = ps1.executeQuery();
                    ResultSet resultSet2 = ps2.executeQuery()
            ) {
                List<ResultSet> resultSets = new ArrayList<>();
                resultSets.add(resultSet1);
                resultSets.add(resultSet2);
                return getInvoicesFromResultSet(resultSets);
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
    }

    /**
     * Get number of invoices in storage
     *
     * @return number of stored invoices
     */
    public int getNumberOfInvoices() {
        final String sql = """
                SELECT COUNT(invoice_id) AS n
                FROM public.invoices
                """;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                resultSet.next();
                return resultSet.getInt("n");
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
    }

    /**
     * Add single invoice to the repository
     *
     * @param invoice invoice to add
     * @return true if success
     */
    @Override
    public boolean add(Invoice invoice) {

        final String invoiceCreateSql = """
                INSERT INTO public.invoices
                (invoice_id, created_at, name)
                VALUES (uuid(?), ?, ?)
                """;

        enum CreateInvoice {
            ZERO,
            INDEX_INVOICE_ID,
            INDEX_INVOICE_CREATED_AT,
            INDEX_INVOICE_NAME
        }

        try (PreparedStatement ps = connection.prepareStatement(invoiceCreateSql)) {

            // invoice_id
            ps.setString(CreateInvoice.INDEX_INVOICE_ID.ordinal(), invoice.getId());

            // created_at
            ps.setTimestamp(CreateInvoice.INDEX_INVOICE_CREATED_AT.ordinal(), Timestamp.valueOf(invoice.getCreatedAt()));

            // name
            ps.setString(CreateInvoice.INDEX_INVOICE_NAME.ordinal(), invoice.getName());

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean add(Optional<Invoice> invoice) {
        if (invoice.isEmpty()) {
            return false;
        }
        return this.add(invoice.get());
    }

    @Override
    public boolean addList(List<Invoice> invoices) {
        if (invoices == null) {
            throw new IllegalArgumentException("invoices list is empty");
        }
        invoices.forEach(this::add);
        return true;
    }

    /**
     * Update single invoice in the repository
     *
     * @param invoice invoice to update
     * @return true if success
     */
    @Override
    public boolean update(Invoice invoice) {

        final String invoiceUpdateSql = """
                UPDATE public.invoices
                SET (created_at, name)
                = (?, ?)
                WHERE invoice_id = uuid(?)
                """;

        enum UpdateInvoice {
            ZERO,
            INDEX_INVOICE_CREATED_AT,
            INDEX_INVOICE_NAME,
            INDEX_INVOICE_ID
        }

        try (PreparedStatement ps = connection.prepareStatement(invoiceUpdateSql)) {

            // created_at
            ps.setTimestamp(UpdateInvoice.INDEX_INVOICE_CREATED_AT.ordinal(), Timestamp.valueOf(invoice.getCreatedAt()));

            // name
            ps.setString(UpdateInvoice.INDEX_INVOICE_NAME.ordinal(), invoice.getName());

            // invoice_id
            ps.setString(UpdateInvoice.INDEX_INVOICE_ID.ordinal(), invoice.getId());

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    /**
     * Delete single invoice from the repository
     *
     * @param invoiceId invoice id
     * @return true if success
     */
    @Override
    public boolean delete(String invoiceId) {
        final String sql = """
                DELETE FROM public.invoices
                WHERE public.invoices.invoice_id = uuid(?)
                """;

        enum DeleteInvoice {
            ZERO,
            INDEX_INVOICE_ID
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // invoice_id
            ps.setString(DeleteInvoice.INDEX_INVOICE_ID.ordinal(), invoiceId);

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    /**
     * Update invoice timestamp
     *
     * @param invoiceId invoice id
     * @param datetime  new timestamp
     * @return true if success
     */
    public boolean updateInvoiceTimestamp(String invoiceId, LocalDateTime datetime) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("invoice id is null");
        }
        if (datetime == null) {
            throw new IllegalArgumentException("datetime is null");
        }

        final String sql = """
                UPDATE public.invoices
                SET created_at = ?
                WHERE invoice_id = uuid(?)
                """;

        enum UpdateTimestamp {
            ZERO,
            INDEX_INVOICE_CREATED_AT,
            INDEX_INVOICE_ID
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // created_at
            ps.setTimestamp(UpdateTimestamp.INDEX_INVOICE_CREATED_AT.ordinal(), Timestamp.valueOf(datetime));

            // invoice_id
            ps.setString(UpdateTimestamp.INDEX_INVOICE_ID.ordinal(), invoiceId);

            ps.execute();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
        return true;
    }

    /**
     * Get invoices with total price greater than the target total
     *
     * @param total target total cost
     * @return list of invoices with required total
     */
    public List<Optional<Invoice>> getInvoicesMoreExpensiveThan(BigDecimal total) {

        final String sqlInvoicesAutomobiles = """
                SELECT
                
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                automobiles.automobile_id AS %s,
                automobiles.model AS %s,
                automobiles.price AS %s,
                automobiles.components AS %s,
                automobile_manufacturers.name AS %s,
                automobiles.body_type AS %s,
                automobiles.created_at AS %s,
                automobiles.trip_counter AS %s,
                engines.volume AS %s,
                engines.brand AS %s
                
                FROM public.invoices
                JOIN public.automobiles ON invoices.invoice_id = automobiles.invoice_id
                JOIN public.automobile_manufacturers ON automobile_manufacturers.manufacturer_id = automobiles.manufacturer_id
                JOIN public.engines ON engines.engine_id = automobiles.engine_id
                WHERE invoices.invoice_id IN
                (
                    SELECT invoice_id FROM (
                        SELECT invoices.invoice_id AS invoice_id, SUM(automobiles.price) AS total
                        FROM public.invoices
                        JOIN public.automobiles ON invoices.invoice_id = automobiles.invoice_id
                        GROUP BY invoices.invoice_id
                    ) AS invoice_id
                    WHERE total > ?
                )
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        AutomobileTable.LABEL_AUTOMOBILE_ID,
                        AutomobileTable.LABEL_AUTOMOBILE_MODEL,
                        AutomobileTable.LABEL_AUTOMOBILE_PRICE,
                        AutomobileTable.LABEL_AUTOMOBILE_COMPONENTS,
                        AutomobileTable.LABEL_AUTOMOBILE_MANUFACTURER_NAME,
                        AutomobileTable.LABEL_AUTOMOBILE_BODY_TYPE,
                        AutomobileTable.LABEL_AUTOMOBILE_CREATED_AT,
                        AutomobileTable.LABEL_AUTOMOBILE_TRIP_COUNTER,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_VOLUME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_BRAND
                );

        final String sqlInvoicesBicycles = """
                SELECT
                
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                bicycles.bicycle_id AS %s,
                bicycles.model AS %s,
                bicycles.price AS %s,
                bicycles.components AS %s,
                bicycle_manufacturers.name AS %s,
                bicycles.number_of_wheels AS %s
                
                FROM public.invoices
                JOIN public.bicycles ON invoices.invoice_id = bicycles.invoice_id
                JOIN public.bicycle_manufacturers ON bicycle_manufacturers.manufacturer_id = bicycles.manufacturer_id
                WHERE invoices.invoice_id IN
                (
                    SELECT invoice_id FROM (
                        SELECT invoices.invoice_id AS invoice_id, SUM(bicycles.price) AS total
                        FROM public.invoices
                        JOIN public.bicycles ON invoices.invoice_id = bicycles.invoice_id
                        GROUP BY invoices.invoice_id
                    ) AS invoice_id
                    WHERE total > ?
                )
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        BicycleTable.LABEL_BICYCLE_ID,
                        BicycleTable.LABEL_BICYCLE_MODEL,
                        BicycleTable.LABEL_BICYCLE_PRICE,
                        BicycleTable.LABEL_BICYCLE_COMPONENTS,
                        BicycleTable.LABEL_BICYCLE_MANUFACTURER_NAME,
                        BicycleTable.LABEL_BICYCLE_NUMBER_OF_WHEELS
                );

        enum InvoicesMoreExpensiveThan {
            ZERO,
            INDEX_INVOICE_TOTAL
        }

        try (
                PreparedStatement ps1 = connection.prepareStatement(sqlInvoicesAutomobiles);
                PreparedStatement ps2 = connection.prepareStatement(sqlInvoicesBicycles);
        ) {

            // total
            ps1.setFloat(InvoicesMoreExpensiveThan.INDEX_INVOICE_TOTAL.ordinal(), total.floatValue());
            ps2.setFloat(InvoicesMoreExpensiveThan.INDEX_INVOICE_TOTAL.ordinal(), total.floatValue());

            try (
                    ResultSet resultSet1 = ps1.executeQuery();
                    ResultSet resultSet2 = ps2.executeQuery();
            ) {
                List<ResultSet> resultSets = new ArrayList<>();
                resultSets.add(resultSet1);
                resultSets.add(resultSet2);
                return getInvoicesFromResultSet(resultSets);
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
    }

    public List<Optional<Invoice>> groupByTotal() {

        final String sqlInvoicesAutomobiles = """
                SELECT
                
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                automobiles.automobile_id AS %s,
                automobiles.model AS %s,
                automobiles.price AS %s,
                automobiles.components AS %s,
                automobile_manufacturers.name AS %s,
                automobiles.body_type AS %s,
                automobiles.created_at AS %s,
                automobiles.trip_counter AS %s,
                engines.volume AS %s,
                engines.brand AS %s
                
                FROM public.invoices
                JOIN public.automobiles ON invoices.invoice_id = automobiles.invoice_id
                JOIN public.automobile_manufacturers ON automobile_manufacturers.manufacturer_id = automobiles.manufacturer_id
                JOIN public.engines ON engines.engine_id = automobiles.engine_id
                WHERE invoices.invoice_id IN
                (
                SELECT invoice_id FROM (
                       SELECT invoice_id, total FROM (
                           SELECT invoices.invoice_id AS invoice_id, SUM(automobiles.price) AS total
                           FROM public.invoices
                           JOIN public.automobiles ON invoices.invoice_id = automobiles.invoice_id
                           GROUP BY invoices.invoice_id
                           ) AS invoice_totals
                       GROUP BY total, invoice_id
                   ) AS target_invoices
                )
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        AutomobileTable.LABEL_AUTOMOBILE_ID,
                        AutomobileTable.LABEL_AUTOMOBILE_MODEL,
                        AutomobileTable.LABEL_AUTOMOBILE_PRICE,
                        AutomobileTable.LABEL_AUTOMOBILE_COMPONENTS,
                        AutomobileTable.LABEL_AUTOMOBILE_MANUFACTURER_NAME,
                        AutomobileTable.LABEL_AUTOMOBILE_BODY_TYPE,
                        AutomobileTable.LABEL_AUTOMOBILE_CREATED_AT,
                        AutomobileTable.LABEL_AUTOMOBILE_TRIP_COUNTER,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_VOLUME,
                        AutomobileTable.LABEL_AUTOMOBILE_ENGINE_BRAND
                );

        final String sqlInvoicesBicycles = """
                SELECT
                
                invoices.invoice_id AS %s,
                invoices.created_at AS %s,
                invoices.name AS %s,
                
                bicycles.bicycle_id AS %s,
                bicycles.model AS %s,
                bicycles.price AS %s,
                bicycles.components AS %s,
                bicycle_manufacturers.name AS %s,
                bicycles.number_of_wheels AS %s
                
                FROM public.invoices
                JOIN public.bicycles ON invoices.invoice_id = bicycles.invoice_id
                JOIN public.bicycle_manufacturers ON bicycle_manufacturers.manufacturer_id = bicycles.manufacturer_id
                WHERE invoices.invoice_id IN
                (
                SELECT invoice_id FROM (
                       SELECT invoice_id, total FROM (
                           SELECT invoices.invoice_id AS invoice_id, SUM(bicycles.price) AS total
                           FROM public.invoices
                           JOIN public.bicycles ON invoices.invoice_id = bicycles.invoice_id
                           GROUP BY invoices.invoice_id
                           ) AS invoice_totals
                       GROUP BY total, invoice_id
                   ) AS target_invoices
                )
                ORDER BY invoices.invoice_id
                """
                .formatted(
                        InvoiceTable.LABEL_INVOICE_ID,
                        InvoiceTable.LABEL_INVOICE_CREATED_AT,
                        InvoiceTable.LABEL_INVOICE_NAME,

                        BicycleTable.LABEL_BICYCLE_ID,
                        BicycleTable.LABEL_BICYCLE_MODEL,
                        BicycleTable.LABEL_BICYCLE_PRICE,
                        BicycleTable.LABEL_BICYCLE_COMPONENTS,
                        BicycleTable.LABEL_BICYCLE_MANUFACTURER_NAME,
                        BicycleTable.LABEL_BICYCLE_NUMBER_OF_WHEELS
                );

        try (
                Statement statement1 = connection.createStatement();
                Statement statement2 = connection.createStatement();
        ) {
            try (
                    ResultSet resultSet1 = statement1.executeQuery(sqlInvoicesAutomobiles);
                    ResultSet resultSet2 = statement2.executeQuery(sqlInvoicesBicycles);
            ) {
                List<ResultSet> resultSets = new ArrayList<>();
                resultSets.add(resultSet1);
                resultSets.add(resultSet2);
                return getInvoicesFromResultSet(resultSets);
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage());
        }
    }
}
