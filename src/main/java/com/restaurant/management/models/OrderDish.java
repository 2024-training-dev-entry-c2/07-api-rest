package com.restaurant.management.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_INCLUDES_DISH")
public class OrderDish {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dish_id", nullable = false)
  private Dish dish;

  private int quantity;

  public OrderDish(Order order, Dish dish, int quantity) {
    this.order = order;
    this.dish = dish;
    this.quantity = quantity;
  }

  public OrderDish(Dish dish, int quantity) {
    this.dish = dish;
    this.quantity = quantity;
  }

  public OrderDish() {
  }
}
