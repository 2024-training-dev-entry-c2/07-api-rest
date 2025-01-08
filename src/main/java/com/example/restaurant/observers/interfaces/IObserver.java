package com.example.restaurant.observers.interfaces;

import com.example.restaurant.constants.EventType;

public interface IObserver<T> {
    void update(EventType eventType, T entity);
}
