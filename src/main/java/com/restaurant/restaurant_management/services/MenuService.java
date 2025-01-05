package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Menu;
import com.restaurant.restaurant_management.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
  private final MenuRepository menuRepository;

  public MenuService(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  public void saveMenu(Menu menu) {
     menuRepository.save(menu);
  }

  public Optional<Menu> getMenu(Integer id) {
    return menuRepository.findById(id);
  }

  public List<Menu> listMenus() {
    return menuRepository.findAll();
  }

  public Menu updateMenu(Integer id, Menu menu) {
    return menuRepository.findById(id).map( x ->{
      x.setMenuName(menu.getMenuName());
      x.setDescription(menu.getDescription());
      return menuRepository.save(x);
    }).orElseThrow(()-> new RuntimeException("Menu con el id "+id+" no pudo ser actualizado"));
  }

  public void deleteMenu(Integer id) {
    menuRepository.deleteById(id);
  }

}
