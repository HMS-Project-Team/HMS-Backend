package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.MealPlanRequestDto;
import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface MealPlanService {
    MealPlanResponseDto GetByIdMealplan(Long id);
    boolean deleteMealplan(Long id);
    Page<MealPlanResponseDto> getMealPlans(Pageable pageable);
    MealPlanResponseDto updateMealPlan(Long id , MealPlanRequestDto requestDto);
}
