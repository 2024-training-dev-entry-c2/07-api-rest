package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.mapper.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.repositories.DishRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateDishCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public UpdateDishCommand(DishRepository dishRepository, DishMapper dishMapper) {
    this.dishRepository = dishRepository;
    this.dishMapper = dishMapper;
  }

  public DishDTO execute(DishDTO dishDTO) {
    Optional<Dish> optionalDish = dishRepository.findById(dishDTO.getId());
    if (optionalDish.isEmpty()) {
      throw new IllegalArgumentException("No se encontró plato con ID: " + dishDTO.getId());
    }
    Dish dishToUpdate = optionalDish.get();
    dishToUpdate.setName(dishDTO.getName());
    dishToUpdate.setPrice(dishDTO.getPrice());
    Dish updatedDish = dishRepository.save(dishToUpdate);
    return dishMapper.toDTO(updatedDish);
  }
}
