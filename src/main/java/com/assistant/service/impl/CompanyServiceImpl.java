package com.assistant.service.impl;

import com.assistant.client.InvoiceHubClient;
import com.assistant.dto.Company;
import com.assistant.dto.response.CompanyResponse;
import com.assistant.exception.CompanyCouldNotRetrievedException;
import com.assistant.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final InvoiceHubClient invoiceHubClient;

    public CompanyServiceImpl(InvoiceHubClient invoiceHubClient) {
        this.invoiceHubClient = invoiceHubClient;
    }

    @Override
    public Company getLoggedInCompany() {

        ResponseEntity<CompanyResponse> response = invoiceHubClient.getLoggedInCompany();

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            CompanyResponse companyResponse = response.getBody();
            return companyResponse.getData();
        }

        throw new CompanyCouldNotRetrievedException("Company could not retrieved.");
    }
}
