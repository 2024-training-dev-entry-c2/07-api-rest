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

  public Menu addMenu(Menu menu){
    for (Dish dish : menu.getDishes()) {
      dish.setMenu(menu);
    }
    return repository.save(menu);
  }

  public Dish addDishToMenu(Long menuId, Dish dish) {
    Menu menu = repository.findById(menuId)
      .orElseThrow(() -> new RuntimeException("Menú no encontrado"));

    return menu.addDish(dish);
  }

  public Optional<Menu> getMenuById(Long id){
    return repository.findById(id);
  }

  public List<Menu> getMenus(){
    return repository.findAll();
  }

  public Menu updateMenu(Long id, Menu updatedMenu){
    return repository.findById(id).map(m -> {
      m.setName(updatedMenu.getName());

      m.getDishes().forEach(dish -> dish.setMenu(null));
      m.getDishes().clear();

      for (Dish dish : updatedMenu.getDishes()) {
        m.addDish(dish);
        dish.setMenu(m);
      }

      return repository.save(m);
    }).orElseThrow(()-> new RuntimeException("Menú con id " + id + " no se pudo actualizar."));
  }

  public void deleteMenu(Long id){
    Menu menu = repository.findById(id).orElseThrow(() -> new RuntimeException("Menú no encontrado"));
    for (Dish dish : menu.getDishes()) {
      dish.setMenu(null);
    }
    repository.deleteById(id);
  }

}
