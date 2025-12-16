package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.MealPlanRequestDto;
import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;


public interface MealPlanService {
    MealPlanResponseDto updateMealPlan(Long id , MealPlanRequestDto requestDto);
}
