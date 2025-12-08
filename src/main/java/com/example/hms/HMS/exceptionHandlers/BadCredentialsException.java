package com.example.hms.HMS.exceptionHandlers;

public class BadCredentialsException extends RuntimeException {
  public BadCredentialsException(String message) {
    super(message);
  }
}
