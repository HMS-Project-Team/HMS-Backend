package com.example.hms.HMS.services;


import com.example.hms.HMS.dtos.requests.MealPlanRequestDto;
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
public class MealPlanServiceImpl implements MealPlanService {

    private final MealPlanMapper mealPlaneMapper;
    private final MealPlanRepository mealPlanRepository;

    public MealPlanResponseDto updateMealPlan(Long id , MealPlanRequestDto requestDto){

    MealPlan mealPlan = mealPlanRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        if (mealPlanRepository.existsByName(requestDto.getName()) && !mealPlan.getName().equals(requestDto.getName())) {
            throw new RuntimeException("MealPlan name already exists");
        }

        mealPlan.setName(requestDto.getName());
        mealPlan.setCode(requestDto.getCode());
        mealPlan.setDescription(requestDto.getDescription());

        MealPlan updated = mealPlanRepository.save(mealPlan);
        return mealPlaneMapper.toResponseDto(updated);
    }
}
