package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDishCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public DishDTO execute(DishDTO dishDTO) {
    Dish dish = dishMapper.toEntity(dishDTO);
    Dish savedDish = dishRepository.save(dish);
    return dishMapper.toDTO(savedDish);
  }
}
