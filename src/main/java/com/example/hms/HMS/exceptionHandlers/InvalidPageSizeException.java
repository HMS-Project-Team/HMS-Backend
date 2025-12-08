package com.example.hms.HMS.exceptionHandlers;

public class InvalidPageSizeException extends RuntimeException {
  public InvalidPageSizeException(String message) {
    super(message);
  }
}
