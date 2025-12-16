package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import com.example.hms.HMS.entities.ViewType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.ViewTypeMapper;
import com.example.hms.HMS.repositories.ViewTypeRepository;
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
