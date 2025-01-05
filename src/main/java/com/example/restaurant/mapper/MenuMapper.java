package com.example.restaurant.mapper;

import com.example.restaurant.models.dto.MenuDTO;
import com.example.restaurant.models.Menu;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuMapper {

  private final DishMapper dishMapper;

  public MenuMapper(DishMapper dishMapper) {
    this.dishMapper = dishMapper;
  }

  public MenuDTO toDTO(Menu menu) {
    return new MenuDTO(
            menu.getId(),
            menu.getName(),
            menu.getDescription(),
            menu.getDishes() != null ? menu.getDishes()
                    .stream()
                    .map(dishMapper::toDTO)
                    .collect(Collectors.toList()) : null
    );
  }

  public Menu toEntity(MenuDTO menuDTO) {
    Menu menu = new Menu();
    menu.setId(menuDTO.getId());
    menu.setName(menuDTO.getName());
    menu.setDescription(menuDTO.getDescription());
    // TODO: Los platos no se mapean directamente aquí; se asignarán en otra capa si es necesario.
    return menu;
  }
}
