package com.restaurante.restaurante.observer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderSubject {
    private List<OrderObserver> observers = new ArrayList<>();

    private ClientObserver clientObserver;
    private DishObserver dishObserver;

    // Constructor vacío
    public OrderSubject() {
    }

    @Autowired
    public void setClientObserver(ClientObserver clientObserver) {
        this.clientObserver = clientObserver;
        attach(clientObserver);
    }

    @Autowired
    public void setDishObserver(DishObserver dishObserver) {
        this.dishObserver = dishObserver;
        attach(dishObserver);
    }

    public void attach(OrderObserver observer) {
        observers.add(observer);
    }

    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Long orderId) {
        observers.forEach(observer -> observer.update(orderId)); // Actualizar los observadores
    }
}