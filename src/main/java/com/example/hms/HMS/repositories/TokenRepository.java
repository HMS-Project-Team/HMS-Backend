package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Token;
import com.example.hms.HMS.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    List<Token> findByUserAndRevokedFalseAndType(User user, String type);

    Token findTopByUserIdAndTypeOrderByCreatedAtDesc(Long userId, String type);

}
