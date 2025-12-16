package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.ViewTypeService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.VIEW_TYPE)
public class ViewTypeController {

    @Autowired
    ViewTypeService viewTypeService;

    @PostMapping(EndpointBundle.CREATE_VIEW_TYPE)
    public ResponseEntity<ResponseWrapper<ViewTypeResponseDto>> createViewType(
            @Valid @RequestBody ViewTypeRequestDto viewTypeRequestDto) {
        ViewTypeResponseDto createdViewType = viewTypeService.createViewType(viewTypeRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.SAVED_SUCCESSFULLY,
                createdViewType));
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ViewTypeResponseDto>> getViewTypeById(@PathVariable Long id) {
        ViewTypeResponseDto response = viewTypeService.getViewTypeById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                response));
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteViewType(@PathVariable Long id){
        Boolean deleteviewType = viewTypeService.deleteViewType(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.DELETED_SUCCESSFULLY,
                        deleteviewType
                ));
    }
}
