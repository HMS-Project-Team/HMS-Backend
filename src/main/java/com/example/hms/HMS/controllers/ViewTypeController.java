package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.ViewTypeRequestDto;
import com.example.hms.HMS.dtos.responses.ViewTypeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.services.ViewTypeService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.VIEW_TYPE)
@RequiredArgsConstructor
public class ViewTypeController {

    private final ViewTypeService viewTypeService;

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

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<ViewTypeResponseDto>>> getAllViewType(
            Pageable pageable) {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();
        if (page < 0 || size <= 0) {
            throw new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }
        Page<ViewTypeResponseDto> viewTypesPage =
                (Page<ViewTypeResponseDto>) viewTypeService.getAllViewType(pageable);

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.SUCCESS,
                        viewTypesPage
                )
        );
    }

}
