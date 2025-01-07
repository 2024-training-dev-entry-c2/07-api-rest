package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.repositories.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
  private final DishRepository dishRepository;

  public DishService(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
  }

  public void saveDish(Dish dish) {
    dishRepository.save(dish);
  }

  public Optional<Dish> getDish(Integer id) {
    return dishRepository.findById(id);
  }

  public List<Dish> listDishes() {
    return dishRepository.findAll();
  }

  public List<Dish> listDishesByMenuIdAndActive(Integer menuId) {
    return dishRepository.findByMenuIdAndActiveTrue(menuId);
  }

  public Dish updateDish(Integer id, Dish dish) {
    return dishRepository.findById(id).map( x ->{
      x.setDishName(dish.getDishName());
      x.setDescription(dish.getDescription());
      x.setBasePrice(dish.getBasePrice());
      x.setIsPopular(dish.getIsPopular());
      x.setMenu(dish.getMenu());
      x.setActive(dish.getActive());
      return dishRepository.save(x);
    }).orElseThrow(()-> new RuntimeException("Dish con el id "+id+" no pudo ser actualizado"));
  }

}
