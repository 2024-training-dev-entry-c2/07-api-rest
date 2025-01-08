package com.restaurant.management.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "MENUS")
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Dish> dishes;

  public Menu(Long id, String name) {
    this.id = id;
    this.name = name;
    this.dishes = new ArrayList<>();
  }

  public Menu(String name) {
    this.name = name;
    this.dishes = new ArrayList<>();
  }

  public Menu() {
    this.dishes = new ArrayList<>();
  }

  public Dish addDish(Dish dish) {
    dishes.add(dish);
    dish.setMenu(this);
    return dish;
  }

  public void removeDish(Dish dish) {
    dishes.remove(dish);
    dish.setMenu(null);
  }
}
