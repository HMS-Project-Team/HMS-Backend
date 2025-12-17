package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.MealPlanRequestDto;
import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import com.example.hms.HMS.entities.MealPlan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MealPlanMapper {
    MealPlanResponseDto toDto(MealPlan mealPlan);
    MealPlan toEntity(MealPlanRequestDto mealPlanRequestDto);
    MealPlanResponseDto toResponseDto(MealPlan mealPlan);
}
