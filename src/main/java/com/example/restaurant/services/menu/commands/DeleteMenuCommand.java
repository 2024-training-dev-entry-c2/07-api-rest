package com.example.restaurant.services.menu.commands;

import com.example.restaurant.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMenuCommand {

  private final MenuRepository menuRepository;

  public void execute(Long menuId) {
    if (!menuRepository.existsById(menuId)) {
      throw new RuntimeException("No se encontró menú con ID: " + menuId);
    }
    menuRepository.deleteById(menuId);
  }
}
