package com.restaurant.management.utils;

import com.restaurant.management.models.Menu;
import com.restaurant.management.models.dto.MenuRequestDTO;
import com.restaurant.management.models.dto.MenuResponseDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DtoMenuConverter {
  public static Menu toMenu(MenuRequestDTO menuRequestDTO){
    Menu menu = new Menu();
    menu.setName(menuRequestDTO.getName());
    return menu;
  }

  public static MenuResponseDTO toMenuResponseDTO(Menu menu){
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    menuResponseDTO.setId(menu.getId());
    menuResponseDTO.setName(menu.getName());
    menuResponseDTO.setDishes(menu.getDishes().stream()
      .map(DtoDishConverter::toDishResponseDTO)
      .collect(Collectors.toList()));
    return menuResponseDTO;
  }
}
