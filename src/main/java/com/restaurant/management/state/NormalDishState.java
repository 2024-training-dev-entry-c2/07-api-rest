package com.restaurant.management.state;

public class NormalDishState implements DishState {
  @Override
  public float getPrice(float basePrice) {
    return basePrice;
  }
}
