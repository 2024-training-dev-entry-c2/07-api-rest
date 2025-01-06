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
public class UpdateDishCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;


  public DishDTO execute(Long id, DishDTO dishDTO) {
    Optional<Dish> optionalDish = dishRepository.findById(id);
    if (optionalDish.isEmpty()) {
      throw new IllegalArgumentException("No se encontró plato con ID: " + id);
    }
    Dish dishToUpdate = optionalDish.get();
    dishToUpdate.setName(dishDTO.getName());
    dishToUpdate.setPrice(dishDTO.getPrice());
    Dish updatedDish = dishRepository.save(dishToUpdate);
    return dishMapper.toDTO(updatedDish);
  }
}
