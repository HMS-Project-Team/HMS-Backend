package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.TaxRequestDto;
import com.example.hms.HMS.dtos.responses.TaxResponseDto;
import com.example.hms.HMS.entities.Tax;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.TaxMapper;
import com.example.hms.HMS.repositories.TaxRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {
    private TaxRepository taxRepository;
    private TaxMapper taxMapper;


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

    @Override
    public TaxResponseDto getById(Long id) {
        TaxResponseDto taxResponseDto=taxMapper.toDto(taxRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Data not found")
                ));
        return taxResponseDto;
    }

    @Override
    public Page<TaxResponseDto> getAllTax(Pageable pageable) {
        Page<Tax> taxPage = taxRepository.findAll(pageable);
        if (taxPage.isEmpty()) {
            throw new ResourceNotFoundException("No Tax found");
        }
        return taxPage.map(taxMapper::toDto);
    }

    @Override
    public TaxResponseDto updateTax(Long id, TaxRequestDto requestDto) {
        Tax existingTax = taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found with id: " + id));
        taxMapper.updateEntity(existingTax, requestDto);
        Tax updatedTax = taxRepository.save(existingTax);
        return taxMapper.toDto(updatedTax);
    }
}
