package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import com.example.hms.HMS.entities.ViewType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.ViewTypeMapper;
import com.example.hms.HMS.repositories.ViewTypeRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
@RequiredArgsConstructor
public class ViewTypeServiceImpl implements ViewTypeService{

    private final ViewTypeRepository viewTypeRepository;
    private final ViewTypeMapper viewTypeMapper;


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

    @Override
    public boolean deleteViewType(Long id) {
        ViewType deleteViewtype = viewTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found"));

        viewTypeRepository.deleteById(id);
        return true;
    }

    @Override
    public ViewTypeResponseDto updateViewType(Long id, ViewTypeRequestDto viewTypeRequestDto) {

        try {
            ViewType viewTypeExist = viewTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("View type not found"));

            if (viewTypeRepository.existsByNameAndIdNot(viewTypeRequestDto.getName(), id)) {
                throw new DataIntegrityViolationException("View type name already exists");
            }

            viewTypeMapper.updateEntity(viewTypeExist, viewTypeRequestDto);

            ViewType update = viewTypeRepository.save(viewTypeExist);
            return viewTypeMapper.toDto(update);
        }catch(Exception ex){
            throw new RuntimeException("Error saving view type"+ex.getMessage());
        }
    }

}
