package com.example.demo.observer;

import org.springframework.stereotype.Component;

@Component
public class PopularDishObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Notificación para plato popular: " + message);
    }
}
