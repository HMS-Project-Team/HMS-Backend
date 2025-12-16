package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.ViewType;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.repositories.ViewTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewTypeServiceImpl implements ViewTypeService{

    private final ViewTypeRepository viewTypeRepository;

    @Override
    public boolean deleteViewType(Long id) {
        ViewType deleteViewtype = viewTypeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Not found"));

        viewTypeRepository.deleteById(id);
        return true;
    }
}
