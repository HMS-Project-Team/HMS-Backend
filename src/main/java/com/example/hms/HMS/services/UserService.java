package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.User;

public interface UserService {
    User getUserByEmail(String email);
}
