package ua.com.alevel.alexshent;

import ua.com.alevel.alexshent.model.*;
import ua.com.alevel.alexshent.repository.database.AutomobileRepository;
import ua.com.alevel.alexshent.repository.database.BicycleRepository;
import ua.com.alevel.alexshent.repository.database.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DemoJDBC {

    public void addOneAutomobile() {
        AutomobileRepository automobileRepository = new AutomobileRepository();
        AutomobileBuilder automobileBuilder = new AutomobileBuilder();
        automobileBuilder.withId("6bd3546a-1fbd-460e-a429-c7081d48d6e9");
        automobileBuilder.withModel("M1");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B1");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(11, "Brand 1"));
        automobileBuilder.withTripCounter(0);
        List<String> components = new ArrayList<>();
        components.add("component 1");
        components.add("component 2");
        automobileBuilder.withComponents(components);
        Automobile automobile1 = automobileBuilder.build();
        automobileRepository.add(automobile1);
    }

    public void addThreeAutomobiles() {
        AutomobileRepository automobileRepository = new AutomobileRepository();
        List<Automobile> automobiles = new ArrayList<>();
        AutomobileBuilder automobileBuilder = new AutomobileBuilder();
        List<String> components = new ArrayList<>();

        automobileBuilder.withId("e7d8c326-5e32-4846-a047-473f6a905bcd");
        automobileBuilder.withModel("M1");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B1");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(11, "Brand 1"));
        automobileBuilder.withTripCounter(1);
        components.add("component 1");
        components.add("component 2");
        components.add("component 3");
        automobileBuilder.withComponents(components);
        Automobile automobile1 = automobileBuilder.build();

        automobileBuilder.withId("d683477f-2a8d-44a2-98a1-03149c51bda4");
        automobileBuilder.withModel("M2");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B2");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(22, "Brand 2"));
        automobileBuilder.withTripCounter(2);
        components.clear();
        components.add("component 4");
        components.add("component 5");
        components.add("component 6");
        automobileBuilder.withComponents(components);
        Automobile automobile2 = automobileBuilder.build();

        automobileBuilder.withId("7b8bcac8-2c1a-41d7-aad5-dff83bfe1113");
        automobileBuilder.withModel("M3");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B3");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(33, "Brand 3"));
        automobileBuilder.withTripCounter(3);
        components.clear();
        components.add("component 7");
        components.add("component 8");
        components.add("component 9");
        automobileBuilder.withComponents(components);
        Automobile automobile3 = automobileBuilder.build();

        automobiles.add(automobile1);
        automobiles.add(automobile2);
        automobiles.add(automobile3);

        automobileRepository.addList(automobiles);
    }

    public void getAllAutomobiles() {
        AutomobileRepository automobileRepository = new AutomobileRepository();
        List<Optional<Automobile>> automobiles = automobileRepository.getAll();
        automobiles.forEach(System.out::println);
    }

    public void getOneAutomobile() {
        AutomobileRepository automobileRepository = new AutomobileRepository();
        Optional<Automobile> automobile = automobileRepository.getById("6bd3546a-1fbd-460e-a429-c7081d48d6e9");
        System.out.println(automobile);
    }

    public void updateOneAutomobile() {
        AutomobileRepository automobileRepository = new AutomobileRepository();
        Optional<Automobile> automobile = automobileRepository.getById("6bd3546a-1fbd-460e-a429-c7081d48d6e9");
        System.out.println(automobile);

        automobile.get().setModel("MMM");
        automobile.get().setTripCounter(999);
        automobile.get().setManufacturer(AutomobileManufacturers.OPEL);
        automobileRepository.update(automobile.get());

        automobile = automobileRepository.getById("6bd3546a-1fbd-460e-a429-c7081d48d6e9");
        System.out.println(automobile);
    }

    public void updateAllAutomobiles() {
        AutomobileRepository automobileRepository = new AutomobileRepository();
        List<Optional<Automobile>> optionals = automobileRepository.getAll();
        List<Automobile> automobiles = new ArrayList<>();
        optionals.forEach(o -> {
            Automobile automobile = o.get();
            automobile.setTripCounter(-999);
            automobiles.add(automobile);
        });
        automobileRepository.updateList(automobiles);
    }

    public void addOneInvoiceWithTwoAutomobilesAndOneBicycle() {
        final String invoiceId = "58e24640-b607-4f88-9e0b-4f7da64db79d";

        InvoiceRepository invoiceRepository = new InvoiceRepository();
        AutomobileRepository automobileRepository = new AutomobileRepository();
        BicycleRepository bicycleRepository = new BicycleRepository();
        List<String> components = new ArrayList<>();
        InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
        AutomobileBuilder automobileBuilder = new AutomobileBuilder();
        BicycleBuilder bicycleBuilder = new BicycleBuilder();

        invoiceBuilder.withId(invoiceId);
        invoiceBuilder.withCreatedAt(LocalDateTime.now());
        invoiceBuilder.withName("Invoice 1");
        Invoice invoice1 = invoiceBuilder.build();

        automobileBuilder.withId("e7d8c326-5e32-4846-a047-473f6a905bcd");
        automobileBuilder.withModel("M1");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B1");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(11, "Brand 1"));
        automobileBuilder.withTripCounter(1);
        components.add("component 1");
        components.add("component 2");
        components.add("component 3");
        automobileBuilder.withComponents(components);
        automobileBuilder.withInvoiceId(invoiceId);
        Automobile automobile1 = automobileBuilder.build();

        automobileBuilder.withId("d683477f-2a8d-44a2-98a1-03149c51bda4");
        automobileBuilder.withModel("M2");
        automobileBuilder.withPrice(BigDecimal.valueOf(12.00));
        automobileBuilder.withBodyType("B2");
        automobileBuilder.withManufacturer(AutomobileManufacturers.BMW);
        automobileBuilder.withCreatedAt(LocalDateTime.now());
        automobileBuilder.withEngine(new Engine(22, "Brand 2"));
        automobileBuilder.withTripCounter(2);
        components.clear();
        components.add("component 4");
        components.add("component 5");
        components.add("component 6");
        automobileBuilder.withComponents(components);
        automobileBuilder.withInvoiceId(invoiceId);
        Automobile automobile2 = automobileBuilder.build();

        bicycleBuilder.withId("8d18bb12-b5b8-412e-b23e-1a4d89c68fc1");
        bicycleBuilder.withModel("M-b1");
        bicycleBuilder.withPrice(BigDecimal.valueOf(12.00));
        bicycleBuilder.withManufacturer(BicycleManufactures.AAA);
        components.clear();
        components.add("component 7");
        components.add("component 8");
        bicycleBuilder.withComponents(components);
        bicycleBuilder.withNumberOfWheels(2);
        bicycleBuilder.withInvoiceId(invoiceId);
        Bicycle bicycle1 = bicycleBuilder.build();

        // add invoice
        invoiceRepository.add(invoice1);

        // add automobiles
        List<Automobile> automobiles = new ArrayList<>();
        automobiles.add(automobile1);
        automobiles.add(automobile2);
        automobileRepository.addList(automobiles);

        // add bicycle
        bicycleRepository.add(bicycle1);
    }

    public void getOneInvoice() {
        final String invoiceId = "58e24640-b607-4f88-9e0b-4f7da64db79d";
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        Optional<Invoice> invoice = invoiceRepository.getById(invoiceId);
        System.out.println(invoice);
    }

    public void getAllInvoices() {
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        List<Optional<Invoice>> invoices = invoiceRepository.getAll();
        invoices.forEach(System.out::println);
    }

    public void invoiceRepository() {
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        System.out.println("number of invoices = " + invoiceRepository.getNumberOfInvoices());

        invoiceRepository.updateInvoiceTimestamp("58e24640-b607-4f88-9e0b-4f7da64db79d", LocalDateTime.now());

        System.out.println(invoiceRepository.getInvoicesMoreExpensiveThan(BigDecimal.valueOf(41.22)));

        System.out.println(invoiceRepository.groupByTotal());
    }
}
