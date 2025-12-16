package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import com.example.hms.HMS.entities.MealPlan;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.MealPlanMapper;
import com.example.hms.HMS.repositories.MealPlanRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService{

    private  final MealPlanMapper mealPlanMapper;
    private final MealPlanRepository mealPlanRepository;
    @Override
    public MealPlanResponseDto GetByIdMealplan(Long id) {
        MealPlan mealplan = mealPlanRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        return mealPlanMapper.toDto(mealplan);

    }
}
