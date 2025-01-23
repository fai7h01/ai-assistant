package com.assistant.service.impl;

import com.assistant.client.InvoiceHubClient;
import com.assistant.dto.analysis.InvoiceAnalysis;
import com.assistant.dto.analysis.SalesAnalysis;
import com.assistant.dto.response.InvoiceHubResponse;
import com.assistant.exception.InvoiceHubResponseCouldNotRetrievedException;
import com.assistant.service.ReportingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceHubClient invoiceHubClient;

    public ReportingServiceImpl(InvoiceHubClient invoiceHubClient) {
        this.invoiceHubClient = invoiceHubClient;
    }

    @Override
    public SalesAnalysis getSalesAnalysis(String year, String startMonth, String endMonth) {
        ResponseEntity<InvoiceHubResponse<SalesAnalysis>> response = invoiceHubClient.getSalesAnalysis(year, startMonth, endMonth);
        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            return response.getBody().getData();
        }
        throw new InvoiceHubResponseCouldNotRetrievedException("Sales analysis data could not retrieved.");
    }

    @Override
    public InvoiceAnalysis getInvoiceAnalysis(String year, String startMonth, String endMonth) {
        ResponseEntity<InvoiceHubResponse<InvoiceAnalysis>> response = invoiceHubClient.getInvoiceAnalysis(year, startMonth, endMonth);
        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            return response.getBody().getData();
        }
        throw new InvoiceHubResponseCouldNotRetrievedException("Invoice analysis data could not retrieved.");
    }
}
