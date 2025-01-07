package com.example.restaurant.services.observers;

import com.example.restaurant.models.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderEventPublisher implements OrderObservable {
  private final List<OrderObserver> observers = new ArrayList<>();

  @Override
  public void registerObserver(OrderObserver observer) {
    observers.add(observer);
  }

  @Override
  public void notifyObservers(Order order) {
    for (OrderObserver observer : observers) {
      observer.onOrderCreated(order);
    }
  }
}
