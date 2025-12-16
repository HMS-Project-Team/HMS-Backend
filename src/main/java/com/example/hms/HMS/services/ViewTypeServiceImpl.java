package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import com.example.hms.HMS.entities.ViewType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.ViewTypeMapper;
import com.example.hms.HMS.repositories.ViewTypeRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ViewTypeServiceImpl implements ViewTypeService {

    @Autowired
    ViewTypeRepository viewTypeRepository;

    @Autowired
    ViewTypeMapper viewTypeMapper;

    @Override
    public ViewTypeResponseDto createViewType(ViewTypeRequestDto viewTypeRequestDto) {
        if (viewTypeRequestDto.getName() == null || viewTypeRequestDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException(ValidationMessages.REQUIRED_FIELD_MISSING);
        }

        boolean exists = viewTypeRepository.existsByName(viewTypeRequestDto.getName());
        if (exists) {
            throw new DataIntegrityViolationException("View type name already exists.");
        }

        try {
            ViewType viewType = viewTypeMapper.toEntity(viewTypeRequestDto);
            ViewType savedViewType = viewTypeRepository.save(viewType);
            return viewTypeMapper.toResponseDto(savedViewType);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving the ViewType: " + e.getMessage(), e);
        }
    }

    @Override
    public ViewTypeResponseDto getViewTypeById(Long id) {
        ViewType viewType = viewTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));
        return viewTypeMapper.toResponseDto(viewType);
    }
}
