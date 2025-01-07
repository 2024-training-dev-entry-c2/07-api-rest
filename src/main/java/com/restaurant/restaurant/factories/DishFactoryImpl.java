package com.restaurant.restaurant.factories;

import com.restaurant.restaurant.models.DishModel;

public class DishFactoryImpl implements DishFactory {
  @Override
  public DishModel createDish(String name, Double price) {
    if(price <= 0){
      throw new RuntimeException("Dish price cannot be negative");
    }
    DishModel dish = new DishModel();
    dish.setName(name);
    dish.setPrice(price);
    dish.setIsPopular(false);
    return dish;
  }
}
