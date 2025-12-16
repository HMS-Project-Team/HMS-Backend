package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.MealPlanRequestDto;
import com.example.hms.HMS.dtos.responses.MealPlanResponseDto;
import com.example.hms.HMS.entities.MealPlan;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.MealPlanMapper;
import com.example.hms.HMS.repositories.MealPlanRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService{

    private  final MealPlanMapper mealPlanMapper;
    private final MealPlanRepository mealPlanRepository;

    @Override
    public boolean deleteMealplan(Long id){
        if (!mealPlanRepository.existsById(id)){
            throw new ResourceNotFoundException("Meal Plan ID " + id + " Not Found");
        }
        mealPlanRepository.deleteById(id);
        return true;
    }
    @Override
    public MealPlanResponseDto GetByIdMealplan(Long id) {
        MealPlan mealplan = mealPlanRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        return mealPlanMapper.toDto(mealplan);

    }
    @Override
    public Page<MealPlanResponseDto> getMealPlans(Pageable pageable) {
        if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0){
            throw new InvalidPageSizeException("Invalid page or size value");
        }

        Page<MealPlan> mealPlanPage = mealPlanRepository.findAll(pageable);

        if(mealPlanPage.isEmpty()){
            throw new ResourceNotFoundException("No Meal Plans Found");
        }

        return mealPlanPage.map(mealPlanMapper::toDto);
    }


    @Override
    public MealPlanResponseDto updateMealPlan(Long id , MealPlanRequestDto requestDto){

        MealPlan mealPlan = mealPlanRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        if (mealPlanRepository.existsByName(requestDto.getName()) && !mealPlan.getName().equals(requestDto.getName())) {
            throw new RuntimeException("MealPlan name already exists");
        }

        mealPlan.setName(requestDto.getName());
        mealPlan.setCode(requestDto.getCode());
        mealPlan.setDescription(requestDto.getDescription());

        MealPlan updated = mealPlanRepository.save(mealPlan);
        return mealPlanMapper.toDto(updated);
    }
}
