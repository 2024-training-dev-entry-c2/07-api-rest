package com.restaurant.management.utils;

import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.MenuRequestDTO;
import com.restaurant.management.models.dto.MenuResponseDTO;

import java.util.stream.Collectors;

public class DtoMenuConverter {
  public static Menu toMenu(MenuRequestDTO menuRequestDTO){
    return new Menu(
      menuRequestDTO.getName()
    );
  }

  public static MenuResponseDTO toMenuResponseDTO(Menu menu){
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    menuResponseDTO.setId(menu.getId());
    menuResponseDTO.setName(menu.getName());
    menuResponseDTO.setDishes(menu.getDishes().stream()
      .map(DtoDishConverter::toDishResponseDTO)
      .collect(Collectors.toSet()));
    return menuResponseDTO;
  }
}
