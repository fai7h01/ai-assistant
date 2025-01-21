package com.assistant.exception;

public class InvoiceCouldNotRetrievedException extends RuntimeException{

    public InvoiceCouldNotRetrievedException(String message) {
        super(message);
    }
}
