package com.example.restaurant.services.observers;

import com.example.restaurant.models.Order;

public interface OrderObservable {
    void registerObserver(OrderObserver observer);
    void notifyObservers(Order order);
}
