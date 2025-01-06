package com.example.restaurant.services.menu.commands;

import com.example.restaurant.models.Menu;
import com.example.restaurant.models.dto.MenuDTO;
import com.example.restaurant.repositories.MenuRepository;
import com.example.restaurant.mappers.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMenuCommand {

  private final MenuRepository menuRepository;
  private final MenuMapper menuMapper;

  public MenuDTO execute(MenuDTO menuDTO) {
    Menu menu = menuMapper.toEntity(menuDTO);
    menu = menuRepository.save(menu);
    return menuMapper.toDTO(menu);
  }
}
