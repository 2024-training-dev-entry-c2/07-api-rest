package com.training.restaurant.services.interfaces;

import com.training.restaurant.dto.menus.CreateMenuDto;
import com.training.restaurant.dto.menus.MenuDto;
import com.training.restaurant.dto.menus.UpdateMenuDto;
import com.training.restaurant.models.Menu;

import java.util.List;

public interface IMenuService {

    Menu createMenu(CreateMenuDto createMenuDto);
    List<MenuDto> findAllMenus();
    Menu findMenuById(Long id);
    void removeMenu(Long id);
    void updateMenu(Long id, UpdateMenuDto updateMenuDto);
}
