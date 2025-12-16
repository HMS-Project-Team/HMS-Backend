package com.example.hms.HMS.controllers;

import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.repositories.ViewTypeRepository;
import com.example.hms.HMS.services.ViewTypeService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointBundle.VIEW_TYPE)
public class ViewTypeController {

    private final ViewTypeRepository viewTypeRepository;
    private final ViewTypeService viewTypeService;

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
