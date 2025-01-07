package com.restaurante.restaurante.services;

import com.restaurante.restaurante.dto.MenuDTO;

import java.util.List;
import java.util.Optional;

public interface IMenuService {
    void addMenu(MenuDTO menuDTO);
    Optional<MenuDTO> getMenu(Long id);
    List<MenuDTO> getMenus();
    void deleteMenu(Long id);
    MenuDTO updateMenu(Long id, MenuDTO menuDTO);
}
