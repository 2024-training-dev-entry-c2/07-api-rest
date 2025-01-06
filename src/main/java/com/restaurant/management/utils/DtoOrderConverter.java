package com.restaurant.management.utils;

import com.restaurant.management.models.Menu;
import com.restaurant.management.models.Order;
import com.restaurant.management.models.dto.DishResponseDTO;
import com.restaurant.management.models.dto.MenuRequestDTO;
import com.restaurant.management.models.dto.MenuResponseDTO;
import com.restaurant.management.models.dto.OrderRequestDTO;

public class DtoOrderConverter {
  public static Order toOrder(OrderRequestDTO orderRequestDTO){
    return new Order(
      DtoClientConverter.toClient(orderRequestDTO.getClient()),
      orderRequestDTO.getDate()
    );
  }

  public static MenuResponseDTO toMenuResponseDTO(Menu menu){
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    menuResponseDTO.setId(menu.getId());
    menuResponseDTO.setName(menu.getName());
    menuResponseDTO.setDishes(menu.getDishes().stream().map(DtoDishConverter::toDishResponseDTO).toArray(DishResponseDTO[]::new));
    return menuResponseDTO;
  }
}
