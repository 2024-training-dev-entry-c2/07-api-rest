package com.example.demo.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observador) {
        observers.add(observador);
    }

    public void removeObserver(Observer observador) {
        observers.remove(observador);
    }

    protected void notifyObserver() {
        for (Observer observador : observers) {
            observador.update();
        }
    }
}
