package com.restaurant.management.observer;

import com.restaurant.management.models.Order;

public interface IObservable {
  void addObserver(IOrderObserver observer);
  void removeObserver(IOrderObserver observer);
  void notifyObservers(Order order);
}
