package com.assistant.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE("Active"), INACTIVE("Inactive");

    private final String val;

    UserStatus(String val) {
        this.val = val;
    }

}
