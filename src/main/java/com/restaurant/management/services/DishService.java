package com.restaurant.management.services;

import com.restaurant.management.models.Dish;
import com.restaurant.management.models.Menu;
import com.restaurant.management.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
  private final DishRepository repository;

  @Autowired
  public DishService(DishRepository repository) {
    this.repository = repository;
  }

  public Dish addDish(Dish dish){
    return repository.save(dish);
  }

  public Optional<Dish> getDishById(Long id){
    return repository.findById(id);
  }

  public List<Dish> getDishes(){
    return repository.findAll();
  }

  public List<Dish> getDishesByIds(List<Long> ids) {
    List<Dish> dishes = repository.findAllById(ids);
    if (dishes.size() != ids.size()) {
      throw new RuntimeException("Algunos platos no fueron encontrados.");
    }
    return dishes;
  }

  public Dish updateDish(Long id, Dish updatedDish){
    return repository.findById(id).map(d ->{
      d.setName(updatedDish.getName());
      d.setDescription(updatedDish.getDescription());
      d.setPrice(updatedDish.getPrice());
      return repository.save(d);
    }).orElseThrow(()-> new RuntimeException("Plato con id " + id + " no se pudo actualizar."));
  }

  public void deleteDish(Long id){
    Dish dish = repository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado"));
    Menu menu = dish.getMenu();
    if (menu != null) {
      menu.removeDish(dish);
    }
    repository.deleteById(id);
  }
}
