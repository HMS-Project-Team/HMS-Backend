package com.example.hms.HMS.exceptionHandlers;

public class PrivilegeNotFoundException extends RuntimeException {
    public PrivilegeNotFoundException(String message) {
        super(message);
    }
}
