package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.mapper.DishMapper;
import com.example.restaurant.repositories.DishRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListDishesCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public ListDishesCommand(DishRepository dishRepository, DishMapper dishMapper) {
    this.dishRepository = dishRepository;
    this.dishMapper = dishMapper;
  }

  public List<DishDTO> execute() {
    return dishRepository.findAll().stream()
            .map(dishMapper::toDTO)
            .collect(Collectors.toList());
  }
}
