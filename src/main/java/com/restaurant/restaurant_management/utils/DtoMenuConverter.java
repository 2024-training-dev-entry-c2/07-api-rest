package com.restaurant.restaurant_management.utils;

import com.restaurant.restaurant_management.dto.MenuRequestDTO;
import com.restaurant.restaurant_management.dto.MenuResponseDTO;
import com.restaurant.restaurant_management.models.Menu;

public class DtoMenuConverter {

  public static Menu convertToMenu(MenuRequestDTO menuRequestDTO) {
    Menu menu = new Menu();
    menu.setMenuName(menuRequestDTO.getMenuName());
    menu.setDescription(menuRequestDTO.getDescription());
    menu.setActive(menuRequestDTO.getActive());
    return menu;
  }

  public static MenuResponseDTO convertToResponseDTO(Menu menu) {
    MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
    menuResponseDTO.setId(menu.getId());
    menuResponseDTO.setMenuName(menu.getMenuName());
    menuResponseDTO.setDescription(menu.getDescription());
    menuResponseDTO.setActive(menu.getActive());
    return menuResponseDTO;
  }

}
