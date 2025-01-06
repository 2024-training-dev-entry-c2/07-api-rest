package com.restaurant.management.services;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
  private final MenuRepository repository;

  @Autowired
  public MenuService(MenuRepository repository) {
    this.repository = repository;
  }

  public void addMenu(Menu menu){
    repository.save(menu);
  }

  public Optional<Menu> getMenuById(Long id){
    return repository.findById(id);
  }

  public List<Menu> getMenus(){
    return repository.findAll();
  }

  public Menu updateMenu(Long id, Menu updatedMenu){
    return repository.findById(id).map(m ->{
      m.setName(updatedMenu.getName());
      m.setDishes(updatedMenu.getDishes());
      return repository.save(m);
    }).orElseThrow(()-> new RuntimeException("Menú con id " + id + " no se pudo actualizar."));
  }

  public void deleteMenu(Long id){
    repository.deleteById(id);
  }
}
