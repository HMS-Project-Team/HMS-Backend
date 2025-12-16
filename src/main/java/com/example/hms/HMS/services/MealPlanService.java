package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;

public interface MealPlanService {
    MealPlanResponseDto GetByIdMealplan(Long id);
}
