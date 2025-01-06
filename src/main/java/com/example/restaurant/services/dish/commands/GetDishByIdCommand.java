package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetDishByIdCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public DishDTO execute(Long dishId) {
    Optional<Dish> optionalDish = dishRepository.findById(dishId);
    if (optionalDish.isEmpty()) {
      throw new IllegalArgumentException("No se encontró plato con ID: " + dishId);
    }
    return dishMapper.toDTO(optionalDish.get());
  }
}
