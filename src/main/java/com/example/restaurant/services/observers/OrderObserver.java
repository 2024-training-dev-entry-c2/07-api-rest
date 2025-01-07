package com.example.restaurant.services.observers;

import com.example.restaurant.models.Order;

public interface OrderObserver {
  void onOrderCreated(Order order);
}
