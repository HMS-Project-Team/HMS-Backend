package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface ViewTypeService {
    ViewTypeResponseDto updateViewType(Long id, ViewTypeRequestDto viewTypeRequestDto) ;

}
