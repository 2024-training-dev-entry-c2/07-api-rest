package com.restaurant.management.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @ManyToMany
  @JoinTable(
    name = "order_dish",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "dish_id")
  )
  private List<Dish> dishes;

  private Float total;

  public Order(Long id, Client client) {
    this.id = id;
    this.client = client;
    this.dishes = new ArrayList<>();
  }

  public Order() {
    this.dishes = new ArrayList<>();
  }

  public void calculateTotal(){
    total =  dishes.isEmpty() ? 0f : (float) dishes.stream().mapToDouble(Dish::getPrice).sum();
  }
}
