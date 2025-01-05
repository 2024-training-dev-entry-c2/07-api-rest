package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.mapper.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.DishRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetDishByIdCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public GetDishByIdCommand(DishRepository dishRepository, DishMapper dishMapper) {
    this.dishRepository = dishRepository;
    this.dishMapper = dishMapper;
  }

  public DishDTO execute(Long dishId) {
    Optional<Dish> optionalDish = dishRepository.findById(dishId);
    if (optionalDish.isEmpty()) {
      throw new IllegalArgumentException("No se encontró plato con ID: " + dishId);
    }
    return dishMapper.toDTO(optionalDish.get());
  }
}
