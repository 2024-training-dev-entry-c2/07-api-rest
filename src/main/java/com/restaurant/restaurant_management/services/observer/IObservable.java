package com.restaurant.restaurant_management.services.observer;

public interface IObservable {
  void subscribe(String eventType, IObserver listener);
  void notify(String eventType, Object data);
}
