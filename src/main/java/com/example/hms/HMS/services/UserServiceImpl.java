package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.UserMapper;
import com.example.hms.HMS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public Boolean deleteUser(Long id) throws HttpRequestMethodNotSupportedException {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("user not found"));
        userRepository.deleteById(id);
        return true;
    }
}
