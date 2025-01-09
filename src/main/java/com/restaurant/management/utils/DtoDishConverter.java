package com.restaurant.management.utils;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.DishRequestDTO;
import com.restaurant.management.models.dto.DishResponseDTO;

public class DtoDishConverter {
  public static Dish toDish(DishRequestDTO dishRequestDTO, Menu menu){
    Dish dish = new Dish();
    dish.setName(dishRequestDTO.getName());
    dish.setDescription(dishRequestDTO.getDescription());
    dish.setPrice(dishRequestDTO.getPrice());
    dish.setMenu(menu);
    return dish;
  }

  public static DishResponseDTO toDishResponseDTO(Dish dish){
    DishResponseDTO dishResponseDTO = new DishResponseDTO();
    dishResponseDTO.setId(dish.getId());
    dishResponseDTO.setName(dish.getName());
    dishResponseDTO.setDescription(dish.getDescription());
    dishResponseDTO.setPrice(dish.getPrice());
    dishResponseDTO.setState(dish.getState().name());
    dishResponseDTO.setMenuId(dish.getMenu().getId());
    return dishResponseDTO;
  }
}
