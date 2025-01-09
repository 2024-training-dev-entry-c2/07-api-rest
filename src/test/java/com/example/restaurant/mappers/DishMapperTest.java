package com.example.restaurant.mappers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.dto.dish.DishRequestDTO;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishMapperTest {

  private final DishMapper dishMapper = new DishMapper();

  @Test
  @DisplayName("Convertir de RequestDTO a Entity")
  void toEntity() {
    DishRequestDTO dishRequestDTO = new DishRequestDTO();
    dishRequestDTO.setName("Dish 1");
    dishRequestDTO.setPrice(10.0f);

    Dish dish = dishMapper.toEntity(dishRequestDTO);

    assertEquals("Dish 1", dish.getName());
    assertEquals(10.0f, dish.getPrice());
  }

  @Test
  @DisplayName("Convertir de Entity a ResponseDTO")
  void toDTO() {
    Dish dish = new Dish();
    dish.setName("Dish 1");
    dish.setPrice(10.0f);

    DishResponseDTO dishResponseDTO = dishMapper.toDTO(dish);

    assertEquals("Dish 1", dishResponseDTO.getName());
    assertEquals(10.0f, dishResponseDTO.getPrice());
  }
}