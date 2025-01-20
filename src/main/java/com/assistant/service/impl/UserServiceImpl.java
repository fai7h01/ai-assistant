package com.assistant.service.impl;

import com.assistant.client.InvoiceHubClient;
import com.assistant.dto.User;
import com.assistant.dto.response.InvoiceHubResponse;
import com.assistant.exception.CompanyCouldNotRetrievedException;
import com.assistant.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final InvoiceHubClient invoiceHubClient;

    public UserServiceImpl(InvoiceHubClient invoiceHubClient) {
        this.invoiceHubClient = invoiceHubClient;
    }

    @Override
    public User getLoggedInUser() {

        ResponseEntity<InvoiceHubResponse<User>> response = invoiceHubClient.getLoggedInUser();

        if (Objects.requireNonNull(response.getBody()).isSuccess()) {
            InvoiceHubResponse<User> userResponse = response.getBody();
            return userResponse.getData();
        }

        throw new UserCouldNotRetrievedException("User could not retrieved.");
    }
}
