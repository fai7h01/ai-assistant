package com.assistant.service.impl;

import com.assistant.dto.records.*;
import com.assistant.client.InvoiceHubClient;
import com.assistant.dto.Invoice;
import com.assistant.dto.response.InvoiceHubResponse;
import com.assistant.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class InvoicingServiceImpl implements InvoiceService {

    private final InvoiceHubClient invoiceHubClient;

    public InvoicingServiceImpl(InvoiceHubClient invoiceHubClient) {
        this.invoiceHubClient = invoiceHubClient;
    }


    public List<Invoice> getInvoices() {

        ResponseEntity<InvoiceHubResponse<List<Invoice>>> response = invoiceHubClient.getInvoices();

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            log.info("\n\n>>>>> Invoices retrieved from BE: {}", response.getBody().getData());
            return response.getBody().getData();
        }
        throw new IllegalStateException("Response failed.");
    }

    private Invoice findInvoice(String invoiceNo) {
        return getInvoices().stream()
                .filter(invoice -> invoice.getInvoiceNo().equals(invoiceNo))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Invoice Not Found."));
    }


    public InvoiceDetails getInvoiceDetails(String invoiceNo) {
        var invoice = findInvoice(invoiceNo);
        return toInvoiceDetails(invoice);
    }

    @Override
    public InvoiceDetails approveInvoice(String invNo) {

        ResponseEntity<InvoiceHubResponse<Invoice>> response = invoiceHubClient.approveInvoice(invNo);

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            Invoice invoice = response.getBody().getData();
            log.info("\n\n>> Approved Invoice: {}", invoice);
            return toInvoiceDetails(invoice);
        }
        throw new IllegalStateException("Response failed.");
    }

    @Override
    public InvoiceDetails sendInvoiceToEmail(String invNo) {

        ResponseEntity<InvoiceHubResponse<Invoice>> response = invoiceHubClient.sendInvoiceToEmail(invNo);

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            Invoice invoice = response.getBody().getData();
            log.info("\n\n>> Invoice is successfully sent to client!");
            return toInvoiceDetails(invoice);
        }
        throw new IllegalStateException("Response failed.");
    }

    @Override
    public InvoiceDetails createInvoice(String companyTitle) {
        return null;
    }

    private InvoiceDetails toInvoiceDetails(Invoice invoice) {
        return new InvoiceDetails(
                invoice.getInvoiceNo(),
                invoice.getInvoiceStatus(),
                invoice.getDateOfIssue(),
                invoice.getDueDate(),
                invoice.getAcceptDate(),
                invoice.getPaymentTerms(),
                invoice.getNotes(),
                invoice.getCompany(),
                invoice.getPrice(),
                invoice.getTax(),
                invoice.getTotal()
        );
    }

}
