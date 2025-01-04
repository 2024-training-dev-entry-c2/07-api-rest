package com.restaurant.management.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Dish {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Float price;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @ManyToMany(mappedBy = "dishes")
  private Set<Order> orders;

  public Dish(Long id, String name, String description, Float price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public Dish() {
  }
}
