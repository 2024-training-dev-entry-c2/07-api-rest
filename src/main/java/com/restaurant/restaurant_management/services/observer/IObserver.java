package com.restaurant.restaurant_management.services.observer;

public interface IObserver {
  void update(String eventType, Object data);
}
