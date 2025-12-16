package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.ViewTypeService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.VIEW_TYPE)
@RequiredArgsConstructor
public class ViewTypeController {

    private final ViewTypeService viewTypeService;

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ViewTypeResponseDto>> updateViewType(@PathVariable Long id, @Valid @RequestBody ViewTypeRequestDto viewTypeRequestDto){
        try{
        ViewTypeResponseDto isUpdated = viewTypeService.updateViewType(id, viewTypeRequestDto);
        if (isUpdated != null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.UPDATED_SUCCESSFULLY,
                    isUpdated
            ));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }}
        catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}
