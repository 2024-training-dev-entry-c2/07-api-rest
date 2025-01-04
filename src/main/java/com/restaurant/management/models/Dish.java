package com.restaurant.management.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Dish {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Float price;

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  private Boolean popular;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @ManyToMany(mappedBy = "dishes")
  private List<Order> orders;

  public Dish(Long id, String name, String description, Float price, Boolean popular, Menu menu) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.popular = popular;
    this.menu = menu;
    this.orders = new ArrayList<>();
  }

  public Dish() {
    this.orders = new ArrayList<>();
  }
}
