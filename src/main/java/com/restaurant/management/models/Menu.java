package com.restaurant.management.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private Set<Dish> dishes;

  public Menu(Long id, String name) {
    this.id = id;
    this.name = name;
    this.dishes = new HashSet<>();
  }

  public Menu(String name) {
    this.name = name;
    this.dishes = new HashSet<>();
  }

  public Menu() {
    this.dishes = new HashSet<>();
  }
}
