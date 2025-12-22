package com.example.hms.HMS.repositories;

import com.example.hms.HMS.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository <Currency , Long> {
    boolean existsByCurrencyName(String currencyName);
    boolean existsByCode(String code);
}
