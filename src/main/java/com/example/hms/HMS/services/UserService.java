package com.example.hms.HMS.services;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;

public interface UserService {
    Boolean deleteUser(Long id) throws HttpRequestMethodNotSupportedException;
}
