package com.assistant.service.impl;

import com.assistant.client.InvoicingClient;
import com.assistant.dto.Invoice;
import com.assistant.dto.response.InvoiceResponse;
import com.assistant.service.InvoiceService;
import com.assistant.service.InvoicingTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
public class InvoicingServiceImpl implements InvoiceService {

    private final InvoicingClient invoicingClient;

    public InvoicingServiceImpl(InvoicingClient invoicingClient) {
        this.invoicingClient = invoicingClient;
    }


    public List<Invoice> getInvoices(String companyTitle) {


        ResponseEntity<InvoiceResponse<List<Invoice>>> response = invoicingClient.getInvoices(companyTitle);

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            log.info("\n\n>>>>> Invoices retrieved from BE: {}", response.getBody().getData());
            return response.getBody().getData();
        }
        throw new IllegalStateException("Response failed.");
    }

    private Invoice findInvoice(String invoiceNo, String companyTitle) {
        return getInvoices(companyTitle).stream()
                .filter(invoice -> invoice.getInvoiceNo().equals(invoiceNo))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Invoice Not Found."));
    }


    public InvoicingTools.InvoiceDetails getInvoiceDetails(String invoiceNo, String companyTitle) {
        var invoice = findInvoice(invoiceNo, companyTitle);
        return toInvoiceDetails(invoice);
    }

    @Override
    public InvoicingTools.InvoiceDetails approveInvoice(String invNo, String companyTitle) {

        ResponseEntity<InvoiceResponse<Invoice>> response = invoicingClient.approveInvoice(invNo, companyTitle);

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            Invoice invoice = response.getBody().getData();
            log.info("\n\n>> Approved Invoice: {}", invoice);
            return toInvoiceDetails(invoice);
        }
        throw new IllegalStateException("Response failed.");
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
                invoice.getCompany(),
                invoice.getPrice(),
                invoice.getTax(),
                invoice.getTotal()
        );
    }

}
