package com.example.restaurant.services.menu.commands;

import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.MenuDTO;
import com.example.restaurant.repositories.MenuRepository;
import com.example.restaurant.mappers.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateMenuCommand {

  private final MenuRepository menuRepository;
  private final MenuMapper menuMapper;

  public MenuDTO execute(Long menuId, MenuDTO menuDTO) {
    Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró menú con ID: " + menuId));
    menu.setName(menuDTO.getName());
    menu.setDescription(menuDTO.getDescription());
    menu = menuRepository.save(menu);
    return menuMapper.toDTO(menu);
  }
}
