package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.Menu;
import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.models.Dish;
import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import com.example.restaurant.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateDishCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public DishResponseDTO execute(DishRequestDTO dishDTO) {
    Dish dish = dishMapper.toEntity(dishDTO);
    Dish savedDish = dishRepository.save(dish);
    return dishMapper.toDTO(savedDish);
  }
}
