package com.example.restaurant.services.menu.commands;

import com.example.restaurant.models.dto.MenuDTO;
import com.example.restaurant.models.dto.menu.MenuResponseDTO;
import com.example.restaurant.repositories.MenuRepository;
import com.example.restaurant.mappers.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMenuByIdCommand {

  private final MenuRepository menuRepository;
  private final MenuMapper menuMapper;

  public MenuResponseDTO execute(Long menuId) {
    return menuRepository.findById(menuId)
            .map(menuMapper::toDTO)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró menú con ID: " + menuId));
  }
}
