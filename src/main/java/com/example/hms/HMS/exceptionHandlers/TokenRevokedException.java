package com.example.hms.HMS.exceptionHandlers;

public class TokenRevokedException extends RuntimeException {
  public TokenRevokedException(String message) {
    super(message);
  }
}
