package com.example.restaurant.services.handlers;

import com.example.restaurant.models.Dish;
import com.example.restaurant.models.Order;

public interface PriceHandler {
  Float calculatePrice(Float price, Order order, Dish dish);
  void setNextHandler(PriceHandler handler);
}
