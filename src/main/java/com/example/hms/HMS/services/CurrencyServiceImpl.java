package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.CurrencyRequestDto;
import com.example.hms.HMS.dtos.responses.CurrencyResponseDto;
import com.example.hms.HMS.entities.Currency;
import com.example.hms.HMS.entities.ReservationType;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.CurrencyMapper;
import com.example.hms.HMS.repositories.CurrencyRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Currency currency = currencyMapper.toCurrencyEntity(requestDto);

        Currency savedData = currencyRepository.save(currency);

        return currencyMapper.toResponseDto(savedData);
    }

    @Override
    public Boolean deleteCurrency(Long id){
        Currency currency = currencyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException ("Reservation type"+ ValidationMessages.NOT_FOUND));
        currencyRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<CurrencyResponseDto> getAllCurrency(Pageable pageable) {

        if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
            throw new InvalidPageSizeException("Invalid page or size value");
        }

        Page<Currency> currencyPage = currencyRepository.findAll(pageable);


        return currencyPage.map(currencyMapper::toResponseDto);
    }

    @Override
    public CurrencyResponseDto getCurrencyById(Long id) {
        Currency  getCurrency = currencyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));
        return currencyMapper.toResponseDto(getCurrency);
    }

    @Override
    public CurrencyResponseDto updateCurrency(Long id, CurrencyRequestDto currencyRequestDto) {
        Currency updateCurrency = currencyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        updateCurrency.setCurrencyName(currencyRequestDto.getCurrencyName());
        updateCurrency.setCode(currencyRequestDto.getCode());
        updateCurrency.setUnitPrice(currencyRequestDto.getUnitPrice());
        updateCurrency.setStatus(currencyRequestDto.getStatus());

        Currency updatedCurrency = currencyRepository.save(updateCurrency);
        return currencyMapper.toResponseDto(updatedCurrency);
    }
}
