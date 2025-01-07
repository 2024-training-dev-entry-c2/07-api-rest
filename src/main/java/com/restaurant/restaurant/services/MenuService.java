package com.restaurant.restaurant.services;

import com.restaurant.restaurant.models.MenuModel;
import com.restaurant.restaurant.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
  @Autowired
  private MenuRepository menuRepository;

  public MenuModel createMenu(MenuModel menu){
    return menuRepository.save(menu);
  }

  public List<MenuModel> getMenus(){
    return menuRepository.findAll();
  }

  public MenuModel updateMenu(Long id, MenuModel menu){
    return menuRepository.findById(id).map(x -> {
      x.setName(menu.getName());
      x.setDescription(menu.getDescription());
      return menuRepository.save(x);
    }).orElseThrow(()-> new RuntimeException("Menu with id "+id+" not found"));
  }

  public void deleteMenu(Long id){
    menuRepository.deleteById(id);
  }
}
