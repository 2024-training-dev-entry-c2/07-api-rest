package com.example.restaurant.observers;

import com.example.restaurant.constants.EventType;
import com.example.restaurant.observers.interfaces.IObserver;

import java.util.ArrayList;
import java.util.List;

public class GenericSubject<T> {

    private final List<IObserver<T>> observers = new ArrayList<>();

    public void notifyObservers(EventType eventType, T entity) {
        for (IObserver<T> observer : observers) {
            observer.update(eventType, entity);
        }
    }
}
