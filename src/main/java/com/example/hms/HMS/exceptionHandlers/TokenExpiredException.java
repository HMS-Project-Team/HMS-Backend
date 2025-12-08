package com.example.hms.HMS.exceptionHandlers;

public class TokenExpiredException extends RuntimeException {
  public TokenExpiredException(String message) {
    super(message);
  }
}
