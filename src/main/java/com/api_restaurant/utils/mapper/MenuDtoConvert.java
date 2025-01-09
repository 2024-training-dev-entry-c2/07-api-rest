package com.api_restaurant.utils.mapper;

import com.api_restaurant.dto.menu.MenuRequestDTO;
import com.api_restaurant.dto.menu.MenuResponseDTO;
import com.api_restaurant.models.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuDtoConvert {

    @Autowired
    public MenuDtoConvert() {
    }

    public MenuResponseDTO convertToResponseDto(Menu menu) {
        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setId(menu.getId());
        menuResponseDTO.setName(menu.getName());
        menuResponseDTO.setDishes(menu.getDishes().stream()
                .map(DishDtoConvert::convertToResponseDto)
                .collect(Collectors.toList()));
        return menuResponseDTO;
    }

    public Menu convertToMenuEntity(MenuRequestDTO dto) {
        Menu menu = new Menu();
        menu.setName(dto.getName());
        return menu;
    }
}