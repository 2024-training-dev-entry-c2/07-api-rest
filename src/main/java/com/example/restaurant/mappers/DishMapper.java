package com.example.restaurant.mappers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {


  public Dish toEntity(DishRequestDTO dishDTO) {
    Dish dish = new Dish();
    dish.setName(dishDTO.getName());
    dish.setPrice(dishDTO.getPrice());
    return dish;
  }
  public DishResponseDTO toDTO(Dish dish) {
    DishResponseDTO dto = new DishResponseDTO();
    dto.setCustomerId(dish.getDishId());
    dto.setName(dish.getName());
    dto.setPrice(dish.getPrice());
    return dto;
  }
}
