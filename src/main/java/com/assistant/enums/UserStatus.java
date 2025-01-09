package com.assistant.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    Active("Active"), Inactive("Inactive");

    private final String val;

    UserStatus(String val) {
        this.val = val;
    }

}
