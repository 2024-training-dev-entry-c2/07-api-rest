package com.restaurant.management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.management.constants.DishStateEnum;
import com.restaurant.management.state.DishState;
import com.restaurant.management.state.NormalDishState;
import com.restaurant.management.state.PopularDishState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Data
@Table(name = "DISHES")
public class Dish {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private Float price;

  @Enumerated(EnumType.STRING)
  private DishStateEnum state;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  @JsonBackReference
  private Menu menu;

  @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<OrderDish> orderDishes;

  @Transient
  private static final Map<DishStateEnum, DishState> stateBehaviors = Map.of(
    DishStateEnum.NORMAL, new NormalDishState(),
    DishStateEnum.POPULAR, new PopularDishState()
  );

  public Dish(Long id, String name, String description, Float price, Menu menu) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.menu = menu;
    this.orderDishes = new ArrayList<>();
    this.state = DishStateEnum.NORMAL;
  }

  public Dish(String name, String description, Float price, Menu menu) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.menu = menu;
    this.orderDishes = new ArrayList<>();
    this.state = DishStateEnum.NORMAL;
  }

  public Dish() {
    this.orderDishes = new ArrayList<>();
    this.state = DishStateEnum.NORMAL;
  }

  public float getPrice() {
    return stateBehaviors.get(state).getPrice(price);
  }

}
