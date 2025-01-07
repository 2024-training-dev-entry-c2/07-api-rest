package com.restaurante.restaurante.services;

import com.restaurante.restaurante.dto.DishDTO;
import com.restaurante.restaurante.models.Dish;

import java.util.List;
import java.util.Optional;

public interface IDishService {
    DishDTO addDish(DishDTO dishDTO);
    Optional<DishDTO> getDish(Long id);
    List<DishDTO> getDishes();
    void deleteDish(Long id);
    DishDTO updateDish(Long id, DishDTO dishDTO);
    void validateAndSetDefaults(Dish dish);
}
