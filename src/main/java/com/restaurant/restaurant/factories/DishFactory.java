package com.restaurant.restaurant.factories;

import com.restaurant.restaurant.models.DishModel;

public interface DishFactory {
  DishModel createDish(String name, Double price);
}
