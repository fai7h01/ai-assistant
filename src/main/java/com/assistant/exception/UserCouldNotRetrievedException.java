package com.assistant.exception;

public class UserCouldNotRetrievedException extends RuntimeException{

    public UserCouldNotRetrievedException(String message) {
        super(message);
    }
}
