package com.restaurant.management.observer;

import com.restaurant.management.models.Client;
import com.restaurant.management.models.Dish;

public interface IOrderObserver {
  void updateOrder(Client client, Dish dish);
}
