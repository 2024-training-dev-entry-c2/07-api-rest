package com.example.restaurant.services.dish.commands;

import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDishCommand {

  private final DishRepository dishRepository;

  public void execute(Long dishId) {
    if (!dishRepository.existsById(dishId)) {
      throw new IllegalArgumentException("No se encontró plato con ID: " + dishId);
    }
    dishRepository.deleteById(dishId);
  }
}
