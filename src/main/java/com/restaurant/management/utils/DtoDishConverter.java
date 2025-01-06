package com.restaurant.management.utils;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.DishRequestDTO;
import com.restaurant.management.models.dto.DishResponseDTO;

public class DtoDishConverter {
  public static Dish toDish(DishRequestDTO dishRequestDTO, Menu menu){
    return new Dish(
      dishRequestDTO.getName(),
      dishRequestDTO.getDescription(),
      dishRequestDTO.getPrice(),
      menu
    );
  }

  public static DishResponseDTO toDishResponseDTO(Dish dish){
    DishResponseDTO dishResponseDTO = new DishResponseDTO();
    dishResponseDTO.setId(dish.getId());
    dishResponseDTO.setName(dish.getName());
    dishResponseDTO.setDescription(dish.getDescription());
    dishResponseDTO.setPrice(dish.getPrice());
    dishResponseDTO.setMenuId(dish.getMenu() != null ? dish.getMenu().getId() : null);
    return dishResponseDTO;
  }
}
