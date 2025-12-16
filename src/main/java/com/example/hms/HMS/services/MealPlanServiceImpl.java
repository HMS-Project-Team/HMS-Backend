package com.example.hms.HMS.services;

import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.repositories.MealPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService{

    private final MealPlanRepository mealPlanRepository;

    @Override
    public boolean deleteMealplan(Long id) throws HttpRequestMethodNotSupportedException {
        if (!mealPlanRepository.existsById(id)){
            throw new ResourceNotFoundException("Meal Plan ID " + id + " Not Found");
        }
        mealPlanRepository.deleteById(id);
        return true;
    }
}
