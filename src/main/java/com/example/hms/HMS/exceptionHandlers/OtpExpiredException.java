package com.example.hms.HMS.exceptionHandlers;

public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException(String message) {
        super(message);
    }
}
