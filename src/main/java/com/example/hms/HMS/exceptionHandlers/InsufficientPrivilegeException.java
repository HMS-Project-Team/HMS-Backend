package com.example.hms.HMS.exceptionHandlers;

public class InsufficientPrivilegeException extends RuntimeException {
    public InsufficientPrivilegeException(String message) {
        super(message);
    }
}
