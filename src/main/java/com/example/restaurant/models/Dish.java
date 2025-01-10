package com.example.restaurant.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dishId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Float price;

  @ManyToMany(mappedBy = "dishes")
  private List<Order> orders;

  private Long menu;

  public Dish(String name, Float price) {
    this.name = name;
    this.price = price;
  }

  public Dish(Long dishId, String name, Float price) {
    this.dishId = dishId;
    this.name = name;
    this.price = price;
  }
}
