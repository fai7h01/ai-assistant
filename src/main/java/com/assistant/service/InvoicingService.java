package com.assistant.service;

import com.assistant.client.InvoicingClient;
import com.assistant.dto.Invoice;
import com.assistant.dto.response.InvoiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class InvoicingService {

    private final InvoicingClient invoicingClient;

    public InvoicingService(InvoicingClient invoicingClient) {
        this.invoicingClient = invoicingClient;
    }

    public List<Invoice> getInvoices() {

        ResponseEntity<InvoiceResponse> response = invoicingClient.getInvoices();

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            log.info("\n\n>>>>> Invoices retrieved from BE: {}", response.getBody().getData());
            return response.getBody().getData();
        }
        throw new IllegalStateException();
    }

    private Invoice findInvoice(String invoiceNo) {
        return getInvoices().stream()
                .filter(invoice -> invoice.getInvoiceNo().equals(invoiceNo))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Invoice Not Found."));
    }

    public InvoicingTools.InvoiceDetails getInvoiceDetails(String invoiceNo) {
        var invoice = findInvoice(invoiceNo);
        return toInvoiceDetails(invoice);
    }

    private InvoicingTools.InvoiceDetails toInvoiceDetails(Invoice invoice) {
        return new InvoicingTools.InvoiceDetails(
                invoice.getInvoiceNo(),
                invoice.getInvoiceStatus(),
                invoice.getInvoiceType(),
                invoice.getDateOfIssue(),
                invoice.getDueDate(),
                invoice.getAcceptDate(),
                invoice.getPaymentTerms(),
                invoice.getNotes(),
                invoice.getPrice(),
                invoice.getTax(),
                invoice.getTotal()
        );
    }

}
