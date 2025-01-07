package com.restaurant.restaurant_management.services.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements IObservable {
  private Map<String, List<IObserver>> listeners = new HashMap<>();

  public EventManager(String... eventTypes) {
    for (String eventType : eventTypes) {
      this.listeners.put(eventType, new ArrayList<>());
    }
  }

  @Override
  public void subscribe(String eventType, IObserver listener) {
    this.listeners.get(eventType).add(listener);
  }

  @Override
  public void notify(String eventType, Object data) {
    List<IObserver> users = listeners.get(eventType);
    for (IObserver listener : users) {
      listener.update(eventType, data);
    }
  }
}
