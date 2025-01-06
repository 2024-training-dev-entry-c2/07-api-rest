package com.example.restaurant.services.menu.commands;

import com.example.restaurant.models.dto.MenuDTO;
import com.example.restaurant.repositories.MenuRepository;
import com.example.restaurant.mappers.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListMenusCommand {

  private final MenuRepository menuRepository;
  private final MenuMapper menuMapper;

  public List<MenuDTO> execute() {
    return menuRepository.findAll()
            .stream()
            .map(menuMapper::toDTO)
            .toList();
  }
}
