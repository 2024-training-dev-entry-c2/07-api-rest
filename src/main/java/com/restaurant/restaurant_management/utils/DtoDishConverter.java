package com.restaurant.restaurant_management.utils;

import com.restaurant.restaurant_management.dto.DishRequestDTO;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;

public class DtoDishConverter {

  public static Dish convertToDish(DishRequestDTO dishRequestDTO, Menu menu) {
    Dish dish = new Dish();
    dish.setDishName(dishRequestDTO.getDishName());
    dish.setDescription(dishRequestDTO.getDescription());
    dish.setBasePrice(dishRequestDTO.getBasePrice());
    dish.setIsPopular(dishRequestDTO.getIsPopular());
    dish.setMenu(menu);
    return dish;
  }
}
