package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.mapper.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.DishRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateDishCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public CreateDishCommand(DishRepository dishRepository, DishMapper dishMapper) {
    this.dishRepository = dishRepository;
    this.dishMapper = dishMapper;
  }

  public DishDTO execute(DishDTO dishDTO) {
    Dish dish = dishMapper.toEntity(dishDTO);
    Dish savedDish = dishRepository.save(dish);
    return dishMapper.toDTO(savedDish);
  }
}
