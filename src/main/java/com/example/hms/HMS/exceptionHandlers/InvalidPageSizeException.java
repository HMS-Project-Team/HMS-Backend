package com.example.hms.HMS.exceptionHandlers;

public class InvalidPageSizeException extends RuntimeException {
    public static final String INVALID_PAGE_SIZE_MSG = "Invalid Page Size.";

    public InvalidPageSizeException(String message) {
        super(message);
    }
}
