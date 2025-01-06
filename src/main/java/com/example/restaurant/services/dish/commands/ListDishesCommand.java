package com.example.restaurant.services.dish.commands;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.mappers.DishMapper;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListDishesCommand {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public List<DishDTO> execute() {
    return dishRepository.findAll().stream()
            .map(dishMapper::toDTO)
            .toList();
  }
}
