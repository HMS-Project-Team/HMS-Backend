package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ViewTypeService {
    ViewTypeResponseDto createViewType(ViewTypeRequestDto viewTypeRequestDto);
    ViewTypeResponseDto getViewTypeById(Long id);
    boolean deleteViewType(Long id);
    ViewTypeResponseDto updateViewType(Long id, ViewTypeRequestDto viewTypeRequestDto) ;
    Page<ViewTypeResponseDto> getAllViewType(Pageable pageable);
}
