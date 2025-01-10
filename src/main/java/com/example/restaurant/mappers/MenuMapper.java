package com.example.restaurant.mappers;

import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.dish.DishResponseDTO;
import com.example.restaurant.models.dto.menu.MenuRequestDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuMapper {

  private final DishRepository dishRepository;
  private final DishMapper dishMapper;

  public Menu toEntity(MenuRequestDTO menuDTO) {
    Menu menu = new Menu();
    menu.setName(menuDTO.getName());
    menu.setDescription(menuDTO.getDescription());
    menu.setDishes(dishRepository.findAllById(menuDTO.getDishIds()));
    return menu;
  }

  public MenuResponseDTO toDTO(Menu menu) {
    List<DishResponseDTO> mappedDishes = menu.getDishes().stream().map(dish -> dishMapper.toDTO(dish)).toList();
    MenuResponseDTO dto = new MenuResponseDTO();
    dto.setMenuId(menu.getMenuId());
    dto.setName(menu.getName());
    dto.setDescription(menu.getDescription());
    dto.setDishes(mappedDishes);
    return dto;
  }
}
