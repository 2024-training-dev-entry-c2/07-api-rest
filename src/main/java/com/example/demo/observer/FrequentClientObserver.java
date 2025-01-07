package com.example.demo.observer;

import org.springframework.stereotype.Component;

@Component
public class FrequentClientObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Notificación para cliente frecuente: " + message);
    }
}
