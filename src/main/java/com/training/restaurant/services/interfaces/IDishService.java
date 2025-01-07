package com.training.restaurant.services.interfaces;

import com.training.restaurant.dto.dishes.UpdateDishDto;
import com.training.restaurant.models.Dishes;

import java.util.List;

public interface IDishService {
    Dishes createDish(Dishes dishes);
    List<Dishes> createAllDishes(List<Dishes> dishes);
    List<Dishes> findAllDishes();
    Dishes findDishByName(String name);
    Dishes findDishById(Long id);
    Dishes updateDish(Long id, UpdateDishDto updateDishDto);
    void removeDish(Long id);
}
