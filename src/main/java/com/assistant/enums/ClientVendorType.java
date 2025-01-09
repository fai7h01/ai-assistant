package com.assistant.enums;

import lombok.Getter;

@Getter
public enum ClientVendorType {

    Vendor("Vendor"),
    Client("Client");

    private final String value;

    ClientVendorType(String value) {
        this.value = value;
    }

}
