package com.restaurant.restaurant_management.services;

import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.repositories.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
  private final DishRepository dishRepository;

  public DishService(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
  }

  public void saveDish(Dish dish) {
    dishRepository.save(dish);
  }

  public List<Dish> listDishes() {
    return dishRepository.findAll();
  }

  public List<Dish> listDishesByMenuId(Integer menuId) {
    return dishRepository.findByMenuId(menuId);
  }

}
