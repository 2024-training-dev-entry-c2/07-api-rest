package com.example.restaurant.mapper;

import com.example.restaurant.models.dto.DishDTO;
import com.example.restaurant.models.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

  public DishDTO toDTO(Dish dish) {
    return new DishDTO(
            dish.getId(),
            dish.getName(),
            dish.getPrice(),
            dish.getMenu() != null ? (dish.getMenu().getId()) : null
    );
  }

  public Dish toEntity(DishDTO dishDTO) {
    Dish dish = new Dish();
    dish.setId(dishDTO.getId());
    dish.setName(dishDTO.getName());
    dish.setPrice(dishDTO.getPrice());
    // El menú no se mapea completamente aquí, sólo el id; se asignará en otra capa si es necesario.
    return dish;
  }
}
