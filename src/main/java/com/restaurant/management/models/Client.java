package com.restaurant.management.models;

import jakarta.persistence.CascadeType;
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

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Order> orders;

  public Client(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public Client() {
  }
}
