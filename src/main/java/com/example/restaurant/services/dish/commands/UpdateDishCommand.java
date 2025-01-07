package com.example.restaurant.services.dish.commands;

import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateDishCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;


  public DishResponseDTO execute(Long dishId, DishRequestDTO dishDTO) {
    Optional<Dish> existingDish = dishRepository.findById(dishId);
    if (existingDish.isEmpty()) {
      throw new RuntimeException("No se encontró plato con ID: " + dishId);
    }
    Dish dishToUpdate = dishMapper.toEntity(dishDTO);
    Dish updatedDish = dishRepository.save(dishToUpdate);
    return dishMapper.toDTO(updatedDish);
  }
}
