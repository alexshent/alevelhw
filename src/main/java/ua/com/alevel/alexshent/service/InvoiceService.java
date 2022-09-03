package ua.com.alevel.alexshent.service;

import ua.com.alevel.alexshent.model.Invoice;
import ua.com.alevel.alexshent.repository.database.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class InvoiceService {
    private final InvoiceRepository repository;

    public InvoiceService() {
        this.repository = new InvoiceRepository();
    }

    public void createInvoice(Invoice invoice) {
        repository.add(invoice);
    }

    public int getNumberOfInvoices() {
        return repository.getNumberOfInvoices();
    }

    public boolean updateInvoiceTimestamp(String invoiceId, LocalDateTime datetime) {
        return repository.updateInvoiceTimestamp(invoiceId, datetime);
    }

    public List<Optional<Invoice>> getInvoicesMoreExpensiveThan(BigDecimal total) {
        return repository.getInvoicesMoreExpensiveThan(total);
    }

    public List<Optional<Invoice>> groupByTotal() {
        return repository.groupByTotal();
    }
}
