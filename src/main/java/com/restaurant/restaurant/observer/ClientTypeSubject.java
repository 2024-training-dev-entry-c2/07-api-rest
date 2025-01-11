package com.restaurant.restaurant.observer;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientTypeSubject {
  private final List<IClientTypeObserver> observers = new java.util.ArrayList<>();

  public void addObserver(IClientTypeObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(IClientTypeObserver observer) {
    observers.remove(observer);
  }

  public void notifyObservers(Long clientId, String oldType, String newType) {
    observers.forEach(observer -> observer.onClientTypeChange(clientId, oldType, newType));
  }
}
