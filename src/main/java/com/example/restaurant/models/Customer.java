package com.example.restaurant.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String name;

  @Column(name = "last_name")
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  private String phone;

  @OneToMany(
          targetEntity = Order.class,
          fetch = FetchType.LAZY,
          mappedBy = "customer",
          orphanRemoval = false
  )
  private List<Order> orders;


}
