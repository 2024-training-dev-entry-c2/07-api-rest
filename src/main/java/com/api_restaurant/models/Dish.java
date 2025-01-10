package com.api_restaurant.models;

import com.api_restaurant.observer.Observable;
import com.api_restaurant.observer.Observer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Dish implements Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @Column(name = "special_dish")
    private Boolean specialDish = false;

    @ManyToOne(targetEntity = Menu.class)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Transient
    private List<Observer> observers = new ArrayList<>();

    public Dish(String name, String description, Double price, Menu menu) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.menu = menu;
    }

    public Dish() {
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}