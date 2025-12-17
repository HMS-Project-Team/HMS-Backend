package com.example.hms.HMS.controllers;


import com.example.hms.HMS.dtos.requests.MealPlanRequestDto;
import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.MealPlanService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(EndpointBundle.MEAL_PLAN)
@RestController
@RequiredArgsConstructor
public class MealPlanController {
    private final MealPlanService mealPlanService;

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Boolean>> deleteMealplan(@PathVariable Long id){
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
    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<MealPlanResponseDto>>> getMealPlans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page<0 || size <= 0) {
            throw  new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<MealPlanResponseDto> mealPlans = mealPlanService.getMealPlans(pageable);

        if (mealPlans.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NOT_FOUND);
        }

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        mealPlans)
        );

    }
    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<MealPlanResponseDto>> updateMealPlan(
            @PathVariable Long id,
            @Valid @RequestBody MealPlanRequestDto requestDto
    ) {
        MealPlanResponseDto updatedMealPlan = mealPlanService.updateMealPlan(id, requestDto);

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.UPDATED_SUCCESSFULLY,
                        updatedMealPlan
                )
        );
    }
    @PostMapping(EndpointBundle.ADD)
    public ResponseEntity<ResponseWrapper<MealPlanResponseDto>> createMealPlan(@Valid @RequestBody MealPlanRequestDto mealPlanRequestDto) {
        MealPlanResponseDto createdMealPlan = mealPlanService.createMealPlan(mealPlanRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.CREATED.getCode(),
                        ValidationMessages.SUCCESS,
                        createdMealPlan
                ));
    }
}
