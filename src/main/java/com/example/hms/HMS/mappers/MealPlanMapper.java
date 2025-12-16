package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import com.example.hms.HMS.entities.MealPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface MealPlanMapper {
    MealPlanResponseDto toResponseDto(MealPlan mealPlan);
    MealPlan toEntity(MealPlanResponseDto mealPlanResponseDto);
}
