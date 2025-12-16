package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.MealPlanService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(EndpointBundle.MEAL_PLAN)
@RestController
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteMealplan(@PathVariable Long id){
        try{
        boolean isDeleted = mealPlanService.deleteMealplan(id);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    true
            ));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    false
            ));
        }}
        catch(HttpRequestMethodNotSupportedException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<MealPlanResponseDto>> GetMealPlan (@PathVariable Long id){
        MealPlanResponseDto response =mealPlanService.GetByIdMealplan(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        response
                )
        );

    }
}
