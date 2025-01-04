package com.restaurant.management.services;

import com.restaurant.management.models.Dish;
import com.restaurant.management.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DishService {
  private final DishRepository repository;

  @Autowired
  public DishService(DishRepository repository) {
    this.repository = repository;
  }

  public void addDish(Dish dish){
    repository.save(dish);
  }

  public Optional<Dish> getDishById(Long id){
    return repository.findById(id);
  }

  public List<Dish> getDishes(){
    return repository.findAll();
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
    repository.deleteById(id);
  }
}
