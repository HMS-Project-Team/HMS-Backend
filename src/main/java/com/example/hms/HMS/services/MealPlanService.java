package com.example.hms.HMS.services;

import org.springframework.web.HttpRequestMethodNotSupportedException;

public interface MealPlanService {
    boolean deleteMealplan(Long id) throws HttpRequestMethodNotSupportedException;

}
