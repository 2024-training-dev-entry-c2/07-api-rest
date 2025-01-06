package com.restaurant.management.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  private Boolean frequent;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Order> orders;

  public Client(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.frequent = false;
    this.orders = new ArrayList<>();
  }

  public Client(String name, String email) {
    this.name = name;
    this.email = email;
    this.frequent = false;
    this.orders = new ArrayList<>();
  }

  public Client() {
    this.frequent = false;
    this.orders = new ArrayList<>();
  }
}
