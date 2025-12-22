package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.CurrencyRequestDto;
import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import com.example.hms.HMS.entities.Currency;
import com.example.hms.HMS.mappers.CurrencyMapper;
import com.example.hms.HMS.repositories.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService{
    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;


    @Override
    public CurrencyResponseDto createNewCurrency(CurrencyRequestDto requestDto) {
        if(requestDto.getCurrencyName().isEmpty() ||
            requestDto.getCode().isEmpty() ||
            requestDto.getUnitPrice()  <= 0 ||
            requestDto.getStatus().isEmpty()){
            throw new IllegalArgumentException("Currency name, code and unit price must be valid");
        }

        if(currencyRepository.existsByCurrencyName(requestDto.getCurrencyName())){
            throw new IllegalArgumentException("Currency name already exists");
        }

        if(currencyRepository.existsByCode(requestDto.getCode())){
            throw new IllegalArgumentException("currency code name already exists");
        }

        Currency currency = new Currency();
        currency.setCurrencyName(requestDto.getCurrencyName());
        currency.setCode(requestDto.getCode());
        currency.setUnitPrice(requestDto.getUnitPrice());
        currency.setStatus(requestDto.getStatus());

        Currency savedData = currencyRepository.save(currency);

        return currencyMapper.toResponseDto(savedData);
    }
}
