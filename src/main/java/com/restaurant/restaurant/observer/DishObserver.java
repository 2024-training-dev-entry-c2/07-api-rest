package com.restaurant.restaurant.observer;

import com.restaurant.restaurant.models.DishModel;
import com.restaurant.restaurant.repositories.DishRepository;

public class DishObserver implements Observer<DishModel> {
  private final DishRepository dishRepository;

  public DishObserver(DishRepository dishRepository) {
    this.dishRepository = dishRepository;
  }

  @Override
  public void update(DishModel dish) {
    Integer orderCount = dishRepository.countOrdersByDishId(dish.getId());
    if(orderCount > 100 && !dish.getIsPopular()){
      dish.setIsPopular(true);
      Double newPrice = dish.getPrice()*1.0573;
      dish.setPrice(newPrice);
      System.out.println("Dish promoted to popular: " + dish.getName());
    }
  }
}
