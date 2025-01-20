package com.assistant.service.impl;

import com.assistant.dto.Company;
import com.assistant.service.CompanyService;

import com.assistant.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final UserService userService;

    public CompanyServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Company getLoggedInCompany() {
        return userService.getLoggedInUser().getCompany();
    }
}
