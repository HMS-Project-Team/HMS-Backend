package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
