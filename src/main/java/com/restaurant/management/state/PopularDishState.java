package com.restaurant.management.state;

public class PopularDishState implements DishState {
  @Override
  public float getPrice(float basePrice) {
    return basePrice * 1.0573f;
  }
}
