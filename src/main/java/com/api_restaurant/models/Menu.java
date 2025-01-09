// java/com/api_restaurant/models/Menu.java
package com.api_restaurant.models;

import com.api_restaurant.observer.Observable;
import com.api_restaurant.observer.Observer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Menu implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<Dish> dishes = new ArrayList<>();

    public Menu(List<Dish> dishes, String name) {
        this.dishes = dishes;
        this.name = name;
    }

    public Menu(String name) {
        this.name = name;
        this.dishes = new ArrayList<>();
    }

    public Menu() {
        this.dishes = new ArrayList<>();
    }

    @Override
    public void update(Observable observable) {
        if (observable instanceof Dish) {
            System.out.println("Nuevo plato agregado al menu: " + ((Dish) observable).getName());
        }
    }
}