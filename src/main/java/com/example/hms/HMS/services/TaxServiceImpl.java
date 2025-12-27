package com.example.hms.HMS.services;

import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {
    private TaxRepository taxRepository;
    private  TaxMapper taxMapper;


    @Override
    public TaxResponseDto addTax(TaxRequestDto requestDto) {
        Tax tax = taxMapper.toEntity(requestDto);
        Tax savedTax = taxRepository.save(tax);
        return taxMapper.toDto(savedTax);
    }

    @Override
    public void deleteTax(Long id) {
        if (!taxRepository.existsById(id)) {
            throw new EntityNotFoundException(ValidationMessages.NOT_FOUND);
        }
        taxRepository.deleteById(id);
    }
}
