package com.example.hms.HMS.controllers;


import com.example.hms.HMS.dtos.requests.MealPlanRequestDto;
import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.MealPlanService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointBundle.MEAL_PLAN)
public class MealPlanController {
    private final MealPlanService mealPlanService;


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
}
