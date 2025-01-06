package com.restaurant.management.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "ORDERS")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderDish> orderDishes;

  private LocalDate date;
  private Float total;

  public Order(Long id, Client client, LocalDate date) {
    this.id = id;
    this.client = client;
    this.date = date;
    this.orderDishes = new ArrayList<>();
  }

  public Order(Client client, LocalDate date) {
    this.client = client;
    this.date = date;
    this.orderDishes = new ArrayList<>();
  }

  public Order() {
    this.orderDishes = new ArrayList<>();
  }

  public void calculateTotal(){
    total =  orderDishes.isEmpty() ? 0f : (float) orderDishes.stream()
      .mapToDouble(orderDish -> orderDish.getDish().getPrice() * orderDish.getQuantity())
      .sum();
  }
}
