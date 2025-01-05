package com.example.restaurant.services.dish.commands;

import com.example.restaurant.repositories.DishRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteDishCommand {

  private final DishRepository dishRepository;

  public DeleteDishCommand(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
  }

  public void execute(Long dishId) {
    if (!dishRepository.existsById(dishId)) {
      throw new IllegalArgumentException("No se encontró plato con ID: " + dishId);
    }
    dishRepository.deleteById(dishId);
  }
}
